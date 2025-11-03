package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "motorcycle")
public class Motorcycle {

    /**
     * Identificador único da moto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "O modelo é obrigatório.")
    @Column(nullable = false, length = 100)
    private String model;

    @NotBlank(message = "A placa é obrigatória.")
    @Column(unique = true, nullable = false, length = 10)
    private String licensePlate;

 
    @NotNull(message = "O status é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MotorcycleStatus status;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking_device_id", unique = true)
    private TrackingDevice trackingDevice;

   
    @OneToOne(mappedBy = "motorcycle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MotorcyclePosition position;
}
