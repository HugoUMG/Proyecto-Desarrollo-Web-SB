package proyecto.contabilidad.repository;

import proyecto.contabilidad.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
  Optional<Cuenta> findByCodigo(String codigo);
}
