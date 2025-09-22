package br.com.mottu.mottu_challenge.controller;

import br.com.mottu.mottu_challenge.repository.BeaconRepository;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import br.com.mottu.mottu_challenge.service.MotorcycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller para funcionalidades do operador de pátio.
 * Acessível por usuários com perfil "OPERATOR" ou "ADMIN".
 */
@Controller
@RequestMapping("/operator")
public class OperatorController {

    private final MotorcycleService motorcycleService;
    private final MotorcycleRepository motorcycleRepository;
    private final BeaconRepository beaconRepository;

    public OperatorController(MotorcycleService motorcycleService, MotorcycleRepository motorcycleRepository, BeaconRepository beaconRepository) {
        this.motorcycleService = motorcycleService;
        this.motorcycleRepository = motorcycleRepository;
        this.beaconRepository = beaconRepository;
    }

    /**
     * Exibe o formulário para associar um beacon a uma moto.
     * Popula o modelo com listas de motos sem beacon e beacons disponíveis.
     */
    @GetMapping("/associate")
    public String showAssociateForm(Model model) {
        model.addAttribute("motorcycles", motorcycleRepository.findByBeaconIsNull());
        model.addAttribute("beacons", beaconRepository.findByMotorcycleIsNull());
        return "operator/associate-form";
    }

    /**
     * Processa a requisição de associação de um beacon a uma moto.
     */
    @PostMapping("/associate")
    public String associateBeacon(@RequestParam Long motorcycleId, @RequestParam Long beaconId, RedirectAttributes redirectAttributes) {
        try {
            motorcycleService.associateBeacon(motorcycleId, beaconId);
            redirectAttributes.addFlashAttribute("successMessage", "Beacon associado com sucesso!");
        } catch (Exception e) {
            // Captura exceções da camada de serviço (ex: EntityNotFoundException, IllegalStateException)
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao associar: " + e.getMessage());
        }
        return "redirect:/operator/associate";
    }
}
