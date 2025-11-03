package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Armazena a última posição conhecida de uma moto dentro de um pátio específico.
 * Esta tabela é atualizada frequentemente pelos dados dos sensores.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "motorcycle_position")
public class MotorcyclePosition {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorcycle_id", nullable = false, unique = true)
    private Motorcycle motorcycle;

  
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_id", nullable = false)
    private Yard yard;


    @NotNull
    @Column(name = "pos_x", nullable = false)
    private Integer posX;


    @NotNull
    @Column(name = "pos_y", nullable = false)
    private Integer posY;

  
    @NotNull
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}