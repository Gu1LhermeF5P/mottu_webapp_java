package br.com.mottu.mottu_challenge.controller;

import br.com.mottu.mottu_challenge.model.Motorcycle;
import br.com.mottu.mottu_challenge.model.MotorcycleStatus;
import br.com.mottu.mottu_challenge.model.TrackingDevice;
import br.com.mottu.mottu_challenge.repository.MotorcycleRepository;
import br.com.mottu.mottu_challenge.repository.TrackingDeviceRepository;
import br.com.mottu.mottu_challenge.service.MotorcycleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * Controller para as funcionalidades de administração.
 * Gerencia o CRUD de Motos e Dispositivos de Rastreamento.
 */
@Controller
@RequestMapping("/admin") // Mapeamento base para todas as rotas de admin
public class AdminController {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleService motorcycleService;
    private final TrackingDeviceRepository trackingDeviceRepository;

    public AdminController(MotorcycleRepository motorcycleRepository, MotorcycleService motorcycleService, TrackingDeviceRepository trackingDeviceRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.motorcycleService = motorcycleService;
        this.trackingDeviceRepository = trackingDeviceRepository;
    }

    // --- MÉTODOS PARA GERENCIAMENTO DE MOTOS ---

    @GetMapping("/motorcycles")
    public String listMotorcycles(Model model) {
        model.addAttribute("motorcycles", motorcycleRepository.findAll());
        return "admin/motorcycle-list";
    }

    @GetMapping("/motorcycles/new")
    public String showCreateForm(Model model) {
        model.addAttribute("motorcycle", new Motorcycle());
        model.addAttribute("statuses", MotorcycleStatus.values());
        return "admin/motorcycle-form";
    }
    
    @GetMapping("/motorcycles/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da moto inválido:" + id));
        model.addAttribute("motorcycle", motorcycle);
        model.addAttribute("statuses", MotorcycleStatus.values());
        return "admin/motorcycle-form";
    }

    @PostMapping("/motorcycles")
    public String createOrUpdateMotorcycle(@Valid @ModelAttribute("motorcycle") Motorcycle motorcycle,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", MotorcycleStatus.values());
            return "admin/motorcycle-form";
        }
        
        if (motorcycle.getId() == null) {
            motorcycleService.createNewMotorcycleWithDevice(motorcycle);
            redirectAttributes.addFlashAttribute("successMessage", "Moto criada com sucesso e dispositivo associado!");
        } else {
            motorcycleRepository.save(motorcycle);
            redirectAttributes.addFlashAttribute("successMessage", "Moto atualizada com sucesso!");
        }

        return "redirect:/admin/motorcycles";
    }

    @GetMapping("/motorcycles/delete/{id}")
    public String deleteMotorcycle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            motorcycleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Moto excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir moto. Verifique se ela não tem dependências.");
        }
        return "redirect:/admin/motorcycles";
    }

    // --- MÉTODOS PARA GERENCIAMENTO DE DISPOSITIVOS ---

    /**
     * Exibe a página de gerenciamento de todos os dispositivos de rastreamento.
     */
    @GetMapping("/devices")
    public String listDevices(Model model) {
        model.addAttribute("devices", trackingDeviceRepository.findAll());
        return "admin/device-management";
    }

    /**
     * Cria um novo dispositivo com um UUID aleatório e o adiciona ao estoque (não vinculado).
     */
    @PostMapping("/devices/add")
    public String addNewDeviceToStock(RedirectAttributes redirectAttributes) {
        TrackingDevice newDevice = new TrackingDevice();
        newDevice.setUuid(UUID.randomUUID().toString());
        trackingDeviceRepository.save(newDevice);

        redirectAttributes.addFlashAttribute("successMessage", "Novo dispositivo adicionado ao estoque!");
        return "redirect:/admin/devices";
    }
}