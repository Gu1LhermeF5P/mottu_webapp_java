package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    List<Motorcycle> findByTrackingDeviceIsNull();

    // NOVO MÉTODO: Busca motos que têm dispositivo mas não têm posição.
    @Query("SELECT m FROM Motorcycle m WHERE m.trackingDevice IS NOT NULL AND m.id NOT IN (SELECT mp.motorcycle.id FROM MotorcyclePosition mp)")
    List<Motorcycle> findAllWithDeviceAndNoPosition();
}