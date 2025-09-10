package proyecto.contabilidad.controller;

import proyecto.contabilidad.dto.BalanceCuentaDTO;
import proyecto.contabilidad.dto.EstadoResultadosDTO;
import proyecto.contabilidad.service.BalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    // Balance de comprobación
    @GetMapping("/comprobacion")
    public ResponseEntity<List<BalanceCuentaDTO>> balanceComprobacion() {
        return ResponseEntity.ok(balanceService.generarBalanceComprobacion());
    }

    // Balance general
    @GetMapping("/general")
    public ResponseEntity<Map<String, List<BalanceCuentaDTO>>> balanceGeneral() {
        return ResponseEntity.ok(balanceService.generarBalanceGeneral());
    }

    // Estado de resultados
    @GetMapping("/resultados")
    public ResponseEntity<EstadoResultadosDTO> estadoResultados() {
        return ResponseEntity.ok(balanceService.generarEstadoResultados());
    }
}
