package br.com.mottu.mottu_challenge.controller;

import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import br.com.mottu.mottu_challenge.repository.TrackingDeviceRepository;
import br.com.mottu.mottu_challenge.service.MotorcycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    private final MotorcycleService motorcycleService;
    private final MotorcycleRepository motorcycleRepository;
    private final TrackingDeviceRepository trackingDeviceRepository;

    public OperatorController(MotorcycleService motorcycleService, MotorcycleRepository motorcycleRepository, TrackingDeviceRepository trackingDeviceRepository) {
        this.motorcycleService = motorcycleService;
        this.motorcycleRepository = motorcycleRepository;
        this.trackingDeviceRepository = trackingDeviceRepository;
    }

    /**
     * Exibe o formulário para associar um dispositivo a uma moto.
     * @param model O container para passar as listas de motos e dispositivos disponíveis.
     * @return O template "operator/associate-form".
     */
    @GetMapping("/associate")
    public String showAssociateForm(Model model) {
        model.addAttribute("motorcycles", motorcycleRepository.findByTrackingDeviceIsNull());
        model.addAttribute("trackingDevices", trackingDeviceRepository.findByMotorcycleIsNull());
        return "operator/associate-form";
    }

    /**
     * Processa a requisição de associação.
     * @param motorcycleId O ID da moto selecionada.
     * @param trackingDeviceId O ID do dispositivo selecionado.
     * @param redirectAttributes Usado para passar mensagens de feedback.
     * @return Redireciona de volta para a página de associação.
     */
    @PostMapping("/associate")
    public String associateTrackingDevice(@RequestParam Long motorcycleId, @RequestParam Long trackingDeviceId, RedirectAttributes redirectAttributes) {
        try {
            motorcycleService.associateTrackingDevice(motorcycleId, trackingDeviceId);
            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo vinculado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao vincular: " + e.getMessage());
        }
        return "redirect:/operator/associate";
    }
}