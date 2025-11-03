package br.com.mottu.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um usuário do sistema com credenciais e um perfil de acesso (role).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

  
    @NotBlank(message = "A senha é obrigatória.")
    @Column(nullable = false)
    private String password;

 
    @NotBlank(message = "O perfil de acesso é obrigatório.")
    @Column(nullable = false, length = 20)
    private String role;
}