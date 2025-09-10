package proyecto.contabilidad.controller;

import proyecto.contabilidad.dto.CuentaDTO;
import proyecto.contabilidad.entity.Cuenta;
import proyecto.contabilidad.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    // Listar todas las cuentas
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listar() {
        List<CuentaDTO> cuentas = cuentaService.listarCuentas().stream()
            .map(c -> new CuentaDTO(c.getId(), c.getCodigo(), c.getNombre(), c.getTipo()))
            .toList();
    return ResponseEntity.ok(cuentas);
    }


    // Buscar una cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> buscar(@PathVariable Long id) {
        return cuentaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva cuenta
    @PostMapping
    public ResponseEntity<CuentaDTO> crear(@RequestBody CuentaDTO dto) {
    var cuenta = cuentaService.crearCuenta(
            new Cuenta(null, dto.getCodigo(), dto.getNombre(), dto.getTipo())
    );
        return ResponseEntity.ok(new CuentaDTO(cuenta.getId(), cuenta.getCodigo(), cuenta.getNombre(), cuenta.getTipo()));
    }

    // Actualizar cuenta existente
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(id, cuenta));
    }

    // Eliminar cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }


}
