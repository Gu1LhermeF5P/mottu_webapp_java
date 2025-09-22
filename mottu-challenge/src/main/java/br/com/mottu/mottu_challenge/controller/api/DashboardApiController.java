package br.com.mottu.mottu_challenge.controller.api;

import br.com.mottu.mottu_challenge.dto.MotorcyclePositionDTO;
import br.com.mottu.mottu_challenge.service.YardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * API REST para fornecer dados para o dashboard.
 * Esta API é consumida via AJAX/Fetch pelo frontend.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {

    private final YardService yardService;

    public DashboardApiController(YardService yardService) {
        this.yardService = yardService;
    }

    /**
     * Retorna a lista de posições de todas as motos em um pátio específico.
     * @param yardId O ID do pátio.
     * @return Uma resposta com a lista de DTOs de posição de moto.
     */
    @GetMapping("/{yardId}/positions")
    public ResponseEntity<List<MotorcyclePositionDTO>> getMotorcyclePositions(@PathVariable Long yardId) {
        try {
            List<MotorcyclePositionDTO> positions = yardService.getMotorcyclePositionsForYard(yardId);
            return ResponseEntity.ok(positions);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
