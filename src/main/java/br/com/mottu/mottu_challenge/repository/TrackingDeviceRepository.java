package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.TrackingDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingDeviceRepository extends JpaRepository<TrackingDevice, Long> {

    /**
     * Busca uma lista de beacons que não estão atualmente associados a nenhuma moto.
     * Útil para a funcionalidade de associação, exibindo apenas os beacons disponíveis.
     * @return Uma lista de entidades Beacon disponíveis (não vinculadas a uma moto).
     */
    List<TrackingDevice> findByMotorcycleIsNull();
}