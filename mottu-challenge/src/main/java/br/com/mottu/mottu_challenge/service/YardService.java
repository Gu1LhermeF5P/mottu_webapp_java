package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.dto.MotorcyclePositionDTO;
import br.com.mottu.mottu_challenge.model.MotorcyclePosition;
import br.com.mottu.mottu_challenge.repository.MotorcyclePositionRepository;
import br.com.mottu.mottu_challenge.repository.YardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para lógica de negócio relacionada aos pátios.
 * A principal função é fornecer os dados de posição para o dashboard.
 */
@Service
public class YardService {

    private final YardRepository yardRepository;
    private final MotorcyclePositionRepository positionRepository;

    public YardService(YardRepository yardRepository, MotorcyclePositionRepository positionRepository) {
        this.yardRepository = yardRepository;
        this.positionRepository = positionRepository;
    }

    /**
     * Busca todas as posições de motos em um pátio específico e as converte para DTOs.
     * Este método alimenta a API consumida pelo frontend do dashboard.
     *
     * @param yardId O ID do pátio.
     * @return Uma lista de DTOs com as informações de posição das motos.
     * @throws jakarta.persistence.EntityNotFoundException se o pátio não for encontrado.
     */
    @Transactional(readOnly = true)
    public List<MotorcyclePositionDTO> getMotorcyclePositionsForYard(Long yardId) {
        if (!yardRepository.existsById(yardId)) {
            throw new jakarta.persistence.EntityNotFoundException("Pátio não encontrado com ID: " + yardId);
        }

        List<MotorcyclePosition> positions = positionRepository.findByYardId(yardId);

        // Converte a lista de entidades para uma lista de DTOs
        return positions.stream()
                .map(pos -> new MotorcyclePositionDTO(
                        pos.getMotorcycle().getLicensePlate(),
                        pos.getMotorcycle().getStatus(),
                        pos.getPosX(),
                        pos.getPosY()
                ))
                .collect(Collectors.toList());
    }
}
