package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.model.MotorcyclePosition;
import br.com.mottu.mottu_challenge.model.MotorcycleStatus;
import br.com.mottu.mottu_challenge.model.Yard;
import br.com.mottu.mottu_challenge.repository.MotorcyclePositionRepository;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import br.com.mottu.mottu_challenge.repository.YardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSimulationService {

    private static final Logger log = LoggerFactory.getLogger(DataSimulationService.class);
    private final MotorcycleRepository motorcycleRepository;
    private final MotorcyclePositionRepository positionRepository;
    private final YardRepository yardRepository;

    // Representação interna das zonas do pátio (coordenadas de 0 a 100)
    private record Zone(int minX, int maxX, int minY, int maxY) {}
    private static final Zone AVAILABLE_ZONE = new Zone(0, 60, 0, 100);
    private static final Zone MAINTENANCE_ZONE = new Zone(61, 100, 0, 45);
    private static final Zone BLOCKED_ZONE = new Zone(61, 100, 46, 100);

    public DataSimulationService(MotorcycleRepository motorcycleRepository, MotorcyclePositionRepository positionRepository, YardRepository yardRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.positionRepository = positionRepository;
        this.yardRepository = yardRepository;
    }

    @Scheduled(fixedRate = 10000) // Executa a cada 10 segundos
    @Transactional
    public void simulateYardActivity() {
        log.info("Executando simulação de atividade no pátio...");

        Optional<Yard> yardOpt = yardRepository.findById(1L);
        if (yardOpt.isEmpty()) {
            log.warn("Nenhum pátio com ID=1 encontrado para a simulação.");
            return;
        }
        Yard yard = yardOpt.get();

        positionNewMotorcycles(yard);
        moveExistingMotorcycles();
    }

    /**
     * Encontra motos que têm um dispositivo vinculado mas ainda não têm uma posição
     * e as adiciona em um local aleatório da zona correta.
     */
    private void positionNewMotorcycles(Yard yard) {
        List<Motorcycle> motorcyclesToPosition = motorcycleRepository.findAllWithDeviceAndNoPosition();
        if (!motorcyclesToPosition.isEmpty()) {
            log.info("Encontradas {} novas motos para posicionar.", motorcyclesToPosition.size());
            for (Motorcycle moto : motorcyclesToPosition) {
                MotorcyclePosition newPosition = new MotorcyclePosition();
                newPosition.setMotorcycle(moto);
                newPosition.setYard(yard);

                Zone targetZone = getZoneForStatus(moto.getStatus());
                newPosition.setPosX(ThreadLocalRandom.current().nextInt(targetZone.minX, targetZone.maxX));
                newPosition.setPosY(ThreadLocalRandom.current().nextInt(targetZone.minY, targetZone.maxY));

                newPosition.setLastUpdated(LocalDateTime.now());
                positionRepository.save(newPosition);
            }
        }
    }

    /**
     * Encontra todas as motos que já estão no pátio e simula um pequeno
     * movimento para cada uma, mantendo-as dentro de suas zonas designadas.
     */
    private void moveExistingMotorcycles() {
        List<MotorcyclePosition> positions = positionRepository.findAll();
        if (positions.isEmpty()) {
            log.info("Nenhuma moto no pátio para mover.");
            return;
        }

        for (MotorcyclePosition pos : positions) {
            Zone targetZone = getZoneForStatus(pos.getMotorcycle().getStatus());

            int moveX = ThreadLocalRandom.current().nextInt(-2, 3); // Movimento entre -2 e +2
            int moveY = ThreadLocalRandom.current().nextInt(-2, 3);

            int newX = pos.getPosX() + moveX;
            int newY = pos.getPosY() + moveY;

            // Garante que a moto se mova apenas dentro dos limites de sua zona
            pos.setPosX(Math.max(targetZone.minX, Math.min(newX, targetZone.maxX - 1)));
            pos.setPosY(Math.max(targetZone.minY, Math.min(newY, targetZone.maxY - 1)));
            pos.setLastUpdated(LocalDateTime.now());
        }
        positionRepository.saveAll(positions);
        log.info("{} posições de motos foram atualizadas na simulação de movimento.", positions.size());
    }

    // Método auxiliar para determinar a zona correta com base no status da moto
    private Zone getZoneForStatus(MotorcycleStatus status) {
        return switch (status) {
            case IN_MAINTENANCE -> MAINTENANCE_ZONE;
            case BLOCKED -> BLOCKED_ZONE;
            default -> AVAILABLE_ZONE; // AVAILABLE, RENTED, etc. usam a zona principal
        };
    }
}