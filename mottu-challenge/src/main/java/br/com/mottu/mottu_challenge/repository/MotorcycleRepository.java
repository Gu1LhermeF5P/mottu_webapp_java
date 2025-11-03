package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    /**
     * Encontra motos que ainda não têm um dispositivo de rastreamento vinculado.
     * Usado na tela "Trocar Dispositivo" para listar motos que precisam de um.
     */
    List<Motorcycle> findByTrackingDeviceIsNull();

    /**
     * Encontra uma moto pela sua placa.
     * Usado no AdminController para validar se uma placa já existe.
     */
    Optional<Motorcycle> findByLicensePlate(String licensePlate);

    /**
     * Encontra motos que TÊM um dispositivo, mas NÃO TÊM uma posição no pátio.
     * Usado pelo DataSimulationService para posicionar novas motos.
     */
    @Query("SELECT m FROM Motorcycle m WHERE m.trackingDevice IS NOT NULL AND m.id NOT IN (SELECT mp.motorcycle.id FROM MotorcyclePosition mp)")
    List<Motorcycle> findAllWithDeviceAndNoPosition();
}