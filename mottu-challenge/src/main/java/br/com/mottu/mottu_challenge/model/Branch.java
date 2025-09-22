package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa uma filial da Mottu em uma cidade específica.
 * Uma filial pode conter um ou mais pátios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branch")
public class Branch {

    /**
     * Identificador único da filial.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da filial (ex: "Centro de Distribuição Tatuapé").
     */
    @NotBlank(message = "O nome da filial é obrigatório.")
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Cidade onde a filial está localizada.
     */
    @NotBlank(message = "A cidade é obrigatória.")
    @Column(nullable = false, length = 100)
    private String city;
}
