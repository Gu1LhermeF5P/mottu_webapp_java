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
@RequestMapping("/operator/associate") // A URL pode ser mantida
public class OperatorController {

    private final MotorcycleService motorcycleService;
    private final MotorcycleRepository motorcycleRepository;
    private final TrackingDeviceRepository trackingDeviceRepository;

    public OperatorController(MotorcycleService motorcycleService, MotorcycleRepository motorcycleRepository, TrackingDeviceRepository trackingDeviceRepository) {
        this.motorcycleService = motorcycleService;
        this.motorcycleRepository = motorcycleRepository;
        this.trackingDeviceRepository = trackingDeviceRepository;
    }

    @GetMapping
    public String showAssociateForm(Model model) {
        // Agora busca todas as motos para permitir a troca
        model.addAttribute("motorcycles", motorcycleRepository.findAll());
        // E busca os dispositivos disponíveis para a troca
        model.addAttribute("trackingDevices", trackingDeviceRepository.findByMotorcycleIsNull());
        return "operator/associate-form";
    }

    @PostMapping
    public String associateTrackingDevice(@RequestParam Long motorcycleId, @RequestParam Long trackingDeviceId, RedirectAttributes redirectAttributes) {
        try {
            // Chama o novo método de "swap" (troca)
            motorcycleService.swapTrackingDevice(motorcycleId, trackingDeviceId);
            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo trocado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao trocar dispositivo: " + e.getMessage());
        }
        return "redirect:/operator/associate";
    }
}