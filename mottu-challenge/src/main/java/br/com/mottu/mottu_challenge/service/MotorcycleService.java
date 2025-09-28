package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.TrackingDevice;
import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.repository.TrackingDeviceRepository;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que encapsula a lógica de negócio relacionada às motos.
 */
@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final TrackingDeviceRepository trackingDeviceRepository;

    public MotorcycleService(MotorcycleRepository motorcycleRepository, TrackingDeviceRepository trackingDeviceRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.trackingDeviceRepository = trackingDeviceRepository;
    }

    /**
     * Associa um beacon disponível a uma moto que ainda não possui um.
     * Contém validações de negócio para garantir a integridade dos dados.
     * A anotação @Transactional garante que a operação seja atômica.
     *
     * @param motorcycleId O ID da moto.
     * @param beaconId O ID do beacon.
     * @throws jakarta.persistence.EntityNotFoundException se a moto ou o beacon não forem encontrados.
     * @throws IllegalStateException se o beacon já estiver em uso.
     */
    @Transactional
    public void associateTrackingDevice(Long motorcycleId, Long trackingDeviceId) {
        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Moto não encontrada com ID: " + motorcycleId));

        TrackingDevice trackingDevice = trackingDeviceRepository.findById(trackingDeviceId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Beacon não encontrado com ID: " + trackingDeviceId));

        // Regra de negócio: um beacon só pode ser associado se não estiver em uso.
        if (trackingDevice.getMotorcycle() != null) {
            throw new IllegalStateException("O beacon com UUID " + trackingDevice.getUuid() + " já está associado à moto de placa " + trackingDevice.getMotorcycle().getLicensePlate());
        }

        // Regra de negócio: a moto não pode já ter um beacon.
        if(motorcycle.getTrackingDevice() != null) {
             throw new IllegalStateException("A moto de placa " + motorcycle.getLicensePlate() + " já possui um beacon associado.");
        }

        motorcycle.setTrackingDevice(trackingDevice);
        motorcycleRepository.save(motorcycle);
    }
}