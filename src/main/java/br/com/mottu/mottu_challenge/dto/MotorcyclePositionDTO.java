package br.com.mottu.mottu_challenge.dto;

import br.com.mottu.mottu_challenge.model.MotorcycleStatus;

/**
 * DTO (Data Transfer Object) que representa uma versão simplificada
 * da posição de uma moto, projetada para ser consumida pela interface do usuário (dashboard).
 *
 * Contém apenas os dados estritamente necessários para a visualização,
 * evitando a exposição desnecessária dos detalhes da entidade JPA.
 *
 * @param licensePlate A placa da moto para identificação.
 * @param status O status atual da moto (para estilização com cores).
 * @param posX A coordenada X da moto no grid do pátio.
 * @param posY A coordenada Y da moto no grid do pátio.
 */
public record MotorcyclePositionDTO(
    String licensePlate,
    MotorcycleStatus status,
    int posX,
    int posY
) {
}