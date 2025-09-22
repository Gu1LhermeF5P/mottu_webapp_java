package br.com.mottu.mottu_challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsável por rotas de acesso geral e autenticação.
 */
@Controller
public class LoginController {

    /**
     * Exibe a página de login customizada.
     * @return O nome da view "login".
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Redireciona para a página inicial (home) da aplicação após o login.
     * @return O nome da view "index".
     */
    @GetMapping("/")
    public String homePage() {
        return "index";
    }
}
