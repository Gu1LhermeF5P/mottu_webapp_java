package br.com.mottu.mottu_challenge.controller;

import br.com.mottu.mottu_challenge.model.Yard;
import br.com.mottu.mottu_challenge.repository.YardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final YardRepository yardRepository;

    public DashboardController(YardRepository yardRepository) {
        this.yardRepository = yardRepository;
    }

    /**
     * Carrega a página de visualização de um pátio específico.
     * @param yardId O ID do pátio a ser visualizado.
     * @param model O container para passar os dados do pátio para a view.
     * @return O template "dashboard/yard-view".
     */
    @GetMapping("/{yardId}")
    public String getYardView(@PathVariable Long yardId, Model model) {
        Yard yard = yardRepository.findById(yardId)
                .orElseThrow(() -> new IllegalArgumentException("ID do pátio inválido: " + yardId));
        model.addAttribute("yard", yard);
        return "dashboard/yard-view";
    }
}