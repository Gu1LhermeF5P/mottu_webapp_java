package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.MotorcyclePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de acesso a dados da entidade MotorcyclePosition.
 * Gerencia os dados de localização em tempo real das motos.
 */
@Repository
public interface MotorcyclePositionRepository extends JpaRepository<MotorcyclePosition, Long> {

    /**
     * Busca todas as posições de motos registradas para um pátio específico.
     * Este é o método principal para alimentar o dashboard de visualização em tempo real.
     *
     * @param yardId O ID do pátio cujas posições de moto são desejadas.
     * @return Uma lista de entidades MotorcyclePosition encontradas no pátio especificado.
     */
    List<MotorcyclePosition> findByYardId(Long yardId);
}