package br.com.mottu.mottu_challenge.service;

import br.com.mottu.mottu_challenge.model.User;
import br.com.mottu.mottu_challenge.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- 1. IMPORTAR
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // <-- 2. ADICIONAR O ENCODER

    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentando carregar o usuário: " + username); // Log para saber que o método foi chamado

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome: " + username));
 
        String senhaDoFormulario = "adminpass"; 
        String hashDoBanco = user.getPassword();

        System.out.println("--- INICIANDO TESTE DE SENHA DENTRO DO SERVIÇO ---");
        System.out.println("Senha que veio do formulário (simulada): " + senhaDoFormulario);
        System.out.println("Hash que veio do banco: " + hashDoBanco);

        boolean senhasBatem = passwordEncoder.matches(senhaDoFormulario, hashDoBanco);
        
        System.out.println("SENHAS BATEM? " + senhasBatem);
        System.out.println("--- FIM DO TESTE ---");


        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }
}