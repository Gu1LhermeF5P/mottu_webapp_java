package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    /**
     * Busca uma lista de motos que ainda não possuem um beacon associado.
     * Usado na tela de associação para listar apenas as motos que precisam de um dispositivo.
     *
     * @return Uma lista de entidades Motorcycle sem beacon.
     */
    List<Motorcycle> findByBeaconIsNull();
}