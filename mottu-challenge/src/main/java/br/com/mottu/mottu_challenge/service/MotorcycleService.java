package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.Beacon;
import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.repository.BeaconRepository;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que encapsula a lógica de negócio relacionada às motos.
 */
@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final BeaconRepository beaconRepository;

    public MotorcycleService(MotorcycleRepository motorcycleRepository, BeaconRepository beaconRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.beaconRepository = beaconRepository;
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
    public void associateBeacon(Long motorcycleId, Long beaconId) {
        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Moto não encontrada com ID: " + motorcycleId));

        Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Beacon não encontrado com ID: " + beaconId));

        // Regra de negócio: um beacon só pode ser associado se não estiver em uso.
        if (beacon.getMotorcycle() != null) {
            throw new IllegalStateException("O beacon com UUID " + beacon.getUuid() + " já está associado à moto de placa " + beacon.getMotorcycle().getLicensePlate());
        }

        // Regra de negócio: a moto não pode já ter um beacon.
        if(motorcycle.getBeacon() != null) {
             throw new IllegalStateException("A moto de placa " + motorcycle.getLicensePlate() + " já possui um beacon associado.");
        }

        motorcycle.setBeacon(beacon);
        motorcycleRepository.save(motorcycle);
    }
}