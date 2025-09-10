package proyecto.contabilidad.repository;
import proyecto.contabilidad.entity.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsientoRepository extends JpaRepository<Asiento, Long> {}