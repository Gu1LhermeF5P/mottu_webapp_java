package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.Yard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YardRepository extends JpaRepository<Yard, Long> {

}
