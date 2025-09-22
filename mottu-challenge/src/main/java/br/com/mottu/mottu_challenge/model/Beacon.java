package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "beacon")
public class Beacon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O UUID do beacon é obrigatório.")
    @Column(unique = true, nullable = false, length = 36)
    private String uuid;


    @OneToOne(mappedBy = "beacon")
    private Motorcycle motorcycle;
}