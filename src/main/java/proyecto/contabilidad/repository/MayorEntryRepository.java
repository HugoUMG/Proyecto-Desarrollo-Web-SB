package proyecto.contabilidad.repository;
import proyecto.contabilidad.entity.MayorEntry;
import proyecto.contabilidad.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MayorEntryRepository extends JpaRepository<MayorEntry, Long> {
  Optional<MayorEntry> findTopByCuentaOrderByFechaDesc(Cuenta cuenta);
}
