package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.model.TrackingDevice;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import br.com.mottu.mottu_challenge.repository.TrackingDeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final TrackingDeviceRepository trackingDeviceRepository;

    public MotorcycleService(MotorcycleRepository motorcycleRepository, TrackingDeviceRepository trackingDeviceRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.trackingDeviceRepository = trackingDeviceRepository;
    }

    @Transactional
    public Motorcycle createNewMotorcycleWithDevice(Motorcycle motorcycle) {
        TrackingDevice newDevice = new TrackingDevice();
        newDevice.setUuid(UUID.randomUUID().toString());
        TrackingDevice savedDevice = trackingDeviceRepository.save(newDevice);
        motorcycle.setTrackingDevice(savedDevice);
        return motorcycleRepository.save(motorcycle);
    }

    /**
     * Troca o dispositivo de uma moto.
     * Desvincula o antigo (se houver) e vincula um novo do estoque.
     */
    @Transactional
    public void swapTrackingDevice(Long motorcycleId, Long newTrackingDeviceId) {
        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com ID: " + motorcycleId));

        TrackingDevice newDevice = trackingDeviceRepository.findById(newTrackingDeviceId)
                .orElseThrow(() -> new EntityNotFoundException("Novo dispositivo não encontrado com ID: " + newTrackingDeviceId));

        if (newDevice.getMotorcycle() != null) {
            throw new IllegalStateException("O novo dispositivo selecionado já está em uso por outra moto.");
        }

        // Simplesmente atribui o novo dispositivo. O JPA se encarrega de atualizar a chave estrangeira.
        motorcycle.setTrackingDevice(newDevice);
        motorcycleRepository.save(motorcycle);
    }
}