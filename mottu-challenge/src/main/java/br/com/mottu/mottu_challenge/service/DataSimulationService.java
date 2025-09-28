package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.model.MotorcyclePosition;
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
 * Serviço para simular dados em tempo real.
 * AGORA COM DUAS FUNÇÕES:
 * 1. Detecta novas motos com dispositivos e as posiciona no pátio.
 * 2. Move as motos já existentes para simular atividade.
 */
@Service
public class DataSimulationService {

    private static final Logger log = LoggerFactory.getLogger(DataSimulationService.class);
    private final MotorcycleRepository motorcycleRepository;
    private final MotorcyclePositionRepository positionRepository;
    private final YardRepository yardRepository;

    public DataSimulationService(MotorcycleRepository motorcycleRepository, MotorcyclePositionRepository positionRepository, YardRepository yardRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.positionRepository = positionRepository;
        this.yardRepository = yardRepository;
    }

    @Scheduled(fixedRate = 15000) // Executa a cada 15 segundos
    @Transactional
    public void simulateYardActivity() {
        log.info("Executando simulação de atividade no pátio...");

        // Usaremos o primeiro pátio do banco como nosso pátio de simulação
        Optional<Yard> yardOpt = yardRepository.findById(1L);
        if (yardOpt.isEmpty()) {
            log.warn("Nenhum pátio encontrado para a simulação. Crie um pátio com ID=1.");
            return;
        }
        Yard yard = yardOpt.get();

        // FUNÇÃO 1: Posicionar novas motos
        positionNewMotorcycles(yard);

        // FUNÇÃO 2: Mover motos existentes
        moveExistingMotorcycles(yard);
    }

    private void positionNewMotorcycles(Yard yard) {
        // Busca todas as motos que têm um dispositivo, mas ainda não têm uma posição
        List<Motorcycle> motorcyclesToPosition = motorcycleRepository.findAllWithDeviceAndNoPosition();
        if (!motorcyclesToPosition.isEmpty()) {
            log.info("Encontradas {} novas motos para posicionar no pátio.", motorcyclesToPosition.size());
            for (Motorcycle moto : motorcyclesToPosition) {
                MotorcyclePosition newPosition = new MotorcyclePosition();
                newPosition.setMotorcycle(moto);
                newPosition.setYard(yard);
                // Gera uma posição inicial aleatória
                newPosition.setPosX(ThreadLocalRandom.current().nextInt(0, yard.getGridWidth()));
                newPosition.setPosY(ThreadLocalRandom.current().nextInt(0, yard.getGridHeight()));
                newPosition.setLastUpdated(LocalDateTime.now());
                positionRepository.save(newPosition);
            }
        }
    }

    private void moveExistingMotorcycles(Yard yard) {
        List<MotorcyclePosition> positions = positionRepository.findByYardId(yard.getId());
        if (positions.isEmpty()) {
            log.info("Nenhuma moto no pátio para mover.");
            return;
        }

        for (MotorcyclePosition pos : positions) {
            int moveX = ThreadLocalRandom.current().nextInt(-1, 2); // -1, 0, or 1
            int moveY = ThreadLocalRandom.current().nextInt(-1, 2); // -1, 0, or 1

            int newX = pos.getPosX() + moveX;
            int newY = pos.getPosY() + moveY;

            // Garante que a moto não saia dos limites do pátio
            pos.setPosX(Math.max(0, Math.min(newX, yard.getGridWidth() - 1)));
            pos.setPosY(Math.max(0, Math.min(newY, yard.getGridHeight() - 1)));
            pos.setLastUpdated(LocalDateTime.now());
        }
        positionRepository.saveAll(positions);
        log.info("{} posições de motos foram atualizadas na simulação de movimento.", positions.size());
    }
}