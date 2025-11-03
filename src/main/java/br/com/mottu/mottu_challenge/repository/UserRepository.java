package br.com.mottu.mottu_challenge.repository;

import br.com.mottu.mottu_challenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo seu nome de usuário (username).
     * @param username O nome de usuário a ser buscado.
     * @return Um Optional contendo o usuário, se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<User> findByUsername(String username);
}