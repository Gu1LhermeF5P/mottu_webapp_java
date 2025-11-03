package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "tracking_devices")
public class TrackingDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 36)
    private String uuid;


    @OneToOne(mappedBy = "trackingDevice") 
    private Motorcycle motorcycle;
}