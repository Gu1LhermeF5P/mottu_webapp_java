package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional; // Importar Optional

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    // NOVO MÉTODO PARA A VALIDAÇÃO
    Optional<Motorcycle> findByLicensePlate(String licensePlate);

    List<Motorcycle> findByTrackingDeviceIsNull();

    @Query("SELECT m FROM Motorcycle m WHERE m.trackingDevice IS NOT NULL AND m.id NOT IN (SELECT mp.motorcycle.id FROM MotorcyclePosition mp)")
    List<Motorcycle> findAllWithDeviceAndNoPosition();
}