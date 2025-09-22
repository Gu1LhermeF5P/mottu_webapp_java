package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.MotorcyclePosition;
import br.com.mottu.mottu_challenge.model.Yard;
import br.com.mottu.mottu_challenge.repository.MotorcyclePositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Serviço para simular dados em tempo real, como o movimento de motos no pátio.
 * Útil para fins de demonstração do dashboard dinâmico.
 */
@Service
public class DataSimulationService {

    private static final Logger log = LoggerFactory.getLogger(DataSimulationService.class);
    private final MotorcyclePositionRepository positionRepository;

    public DataSimulationService(MotorcyclePositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Executa a cada 15 segundos. Busca todas as posições de motos e
     * atualiza suas coordenadas X e Y aleatoriamente para simular movimento.
     */
    @Scheduled(fixedRate = 15000) // Executa a cada 15 segundos (15000 ms)
    public void simulateMotorcycleMovement() {
        log.info("Executando simulação de movimento de motos...");
        List<MotorcyclePosition> positions = positionRepository.findAll();

        if (positions.isEmpty()) {
            log.info("Nenhuma moto no pátio para simular.");
            return;
        }

        for (MotorcyclePosition pos : positions) {
            Yard yard = pos.getYard();
            // Gera um movimento aleatório de -1, 0 ou 1 para X e Y
            int moveX = ThreadLocalRandom.current().nextInt(-1, 2);
            int moveY = ThreadLocalRandom.current().nextInt(-1, 2);

            int newX = pos.getPosX() + moveX;
            int newY = pos.getPosY() + moveY;

            // Garante que a moto não saia dos limites do pátio
            pos.setPosX(Math.max(0, Math.min(newX, yard.getGridWidth() - 1)));
            pos.setPosY(Math.max(0, Math.min(newY, yard.getGridHeight() - 1)));
            pos.setLastUpdated(LocalDateTime.now());
        }

        positionRepository.saveAll(positions);
        log.info("{} posições de motos foram atualizadas na simulação.", positions.size());
    }
}