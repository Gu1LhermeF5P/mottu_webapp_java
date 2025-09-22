package br.com.mottu.mottu_challenge.controller;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.model.MotorcycleStatus;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller para as funcionalidades de administração, acessível apenas por usuários com o perfil "ADMIN".
 * Gerencia o CRUD de Motos.
 */
@Controller
@RequestMapping("/admin/motorcycles")
public class AdminController {

    private final MotorcycleRepository motorcycleRepository;

    public AdminController(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    /**
     * Lista todas as motos cadastradas.
     */
    @GetMapping
    public String listMotorcycles(Model model) {
        model.addAttribute("motorcycles", motorcycleRepository.findAll());
        return "admin/motorcycle-list";
    }

    /**
     * Exibe o formulário para criar uma nova moto.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("motorcycle", new Motorcycle());
        model.addAttribute("statuses", MotorcycleStatus.values());
        return "admin/motorcycle-form";
    }

    /**
     * Processa a submissão do formulário de criação de moto.
     */
    @PostMapping
    public String createOrUpdateMotorcycle(@Valid @ModelAttribute("motorcycle") Motorcycle motorcycle,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", MotorcycleStatus.values());
            return "admin/motorcycle-form";
        }
        
        String message = motorcycle.getId() == null ? "Moto criada com sucesso!" : "Moto atualizada com sucesso!";
        motorcycleRepository.save(motorcycle);
        redirectAttributes.addFlashAttribute("successMessage", message);
        return "redirect:/admin/motorcycles";
    }

    /**
     * Exibe o formulário para editar uma moto existente.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da moto inválido:" + id));
        model.addAttribute("motorcycle", motorcycle);
        model.addAttribute("statuses", MotorcycleStatus.values());
        return "admin/motorcycle-form";
    }

    /**
     * Processa a exclusão de uma moto.
     */
    @GetMapping("/delete/{id}")
    public String deleteMotorcycle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            motorcycleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Moto excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir moto. Verifique se ela não está em uso.");
        }
        return "redirect:/admin/motorcycles";
    }
}
