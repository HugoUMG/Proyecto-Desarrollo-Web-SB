package proyecto.contabilidad.service;

import proyecto.contabilidad.entity.Cuenta;
import proyecto.contabilidad.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepo;

    public CuentaService(CuentaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepo.findAll();
    }

    public Optional<Cuenta> buscarPorId(Long id) {
        return cuentaRepo.findById(id);
    }

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepo.save(cuenta);
    }

    public Cuenta actualizarCuenta(Long id, Cuenta cuenta) {
        return cuentaRepo.findById(id).map(c -> {
            c.setCodigo(cuenta.getCodigo());
            c.setNombre(cuenta.getNombre());
            c.setTipo(cuenta.getTipo());
            return cuentaRepo.save(c);
        }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + id));
    }

    public void eliminarCuenta(Long id) {
        if (!cuentaRepo.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada con id " + id);
        }
        cuentaRepo.deleteById(id);
    }
}
