
package proyecto.contabilidad.controller;
import proyecto.contabilidad.dto.AsientoDTO;
import proyecto.contabilidad.entity.Asiento;
import proyecto.contabilidad.service.AsientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asientos")
public class AsientoController {
  private final AsientoService asientoService;
  public AsientoController(AsientoService asientoService) {
    this.asientoService = asientoService;
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody AsientoDTO dto) {
    Asiento a = asientoService.registrarAsiento(dto);
    return ResponseEntity.ok(a);
  }
}
