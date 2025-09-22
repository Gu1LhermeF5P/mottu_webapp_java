package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yard")
public class Yard {

  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotBlank(message = "O nome do pátio é obrigatório.")
    @Column(nullable = false, length = 100)
    private String name;

   
    @NotNull(message = "A largura da grade é obrigatória.")
    @Positive(message = "A largura da grade deve ser um número positivo.")
    @Column(name = "grid_width", nullable = false)
    private Integer gridWidth;

   
    @NotNull(message = "A altura da grade é obrigatória.")
    @Positive(message = "A altura da grade deve ser um número positivo.")
    @Column(name = "grid_height", nullable = false)
    private Integer gridHeight;

   
    @NotNull(message = "O pátio deve estar associado a uma filial.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}