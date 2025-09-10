package proyecto.contabilidad.repository;
import proyecto.contabilidad.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {}