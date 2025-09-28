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

/**
 * Serviço de simulação de dados em tempo real.
 * Responsável por posicionar motos novas em suas zonas e mover as motos existentes.
 */
@Service
public class DataSimulationService {

    private static final Logger log = LoggerFactory.getLogger(DataSimulationService.class);
    private final MotorcycleRepository motorcycleRepository;
    private final MotorcyclePositionRepository positionRepository;
    private final YardRepository yardRepository;

    // Representação simples das zonas do pátio (coordenadas de 0 a 100)
    private record Zone(int minX, int maxX, int minY, int maxY) {}
    private static final Zone AVAILABLE_ZONE = new Zone(0, 60, 0, 100);
    private static final Zone MAINTENANCE_ZONE = new Zone(61, 100, 0, 45);
    private static final Zone BLOCKED_ZONE = new Zone(61, 100, 46, 100);

    public DataSimulationService(MotorcycleRepository motorcycleRepository, MotorcyclePositionRepository positionRepository, YardRepository yardRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.positionRepository = positionRepository;
        this.yardRepository = yardRepository;
    }

    /**
     * Ponto de entrada da simulação, executado em um intervalo fixo.
     */
    @Scheduled(fixedRate = 10000) // Executa a cada 10 segundos
    @Transactional
    public void simulateYardActivity() {
        log.info("Executando simulação de atividade no pátio...");

        Optional<Yard> yardOpt = yardRepository.findById(1L);
        if (yardOpt.isEmpty()) {
            log.warn("Nenhum pátio encontrado para a simulação.");
            return;
        }
        Yard yard = yardOpt.get();

        positionNewMotorcycles(yard);
        moveExistingMotorcycles();
    }
    
    /**
     * Detecta motos que têm um dispositivo mas não têm uma posição
     * e as posiciona em um local aleatório dentro de sua zona.
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
     * Move as motos existentes em posições aleatórias, mas
     * mantendo-as dentro de suas zonas designadas.
     */
    private void moveExistingMotorcycles() {
        List<MotorcyclePosition> positions = positionRepository.findAll();
        if (positions.isEmpty()) return;

        for (MotorcyclePosition pos : positions) {
            // Garante que a moto se mova apenas dentro de sua zona
            Zone targetZone = getZoneForStatus(pos.getMotorcycle().getStatus());
            
            int moveX = ThreadLocalRandom.current().nextInt(-2, 3); // -2, -1, 0, 1, 2
            int moveY = ThreadLocalRandom.current().nextInt(-2, 3);

            int newX = pos.getPosX() + moveX;
            int newY = pos.getPosY() + moveY;
            
            pos.setPosX(Math.max(targetZone.minX, Math.min(newX, targetZone.maxX - 1)));
            pos.setPosY(Math.max(targetZone.minY, Math.min(newY, targetZone.maxY - 1)));
            pos.setLastUpdated(LocalDateTime.now());
        }
        positionRepository.saveAll(positions);
        log.info("{} posições de motos foram atualizadas na simulação de movimento.", positions.size());
    }
    
    /**
     * Método auxiliar para escolher a zona correta com base no status.
     */
    private Zone getZoneForStatus(MotorcycleStatus status) {
        return switch (status) {
            case IN_MAINTENANCE -> MAINTENANCE_ZONE;
            case BLOCKED -> BLOCKED_ZONE;
            default -> AVAILABLE_ZONE; // AVAILABLE, RENTED, etc. caem aqui
        };
    }
}