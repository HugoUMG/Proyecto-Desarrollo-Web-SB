package proyecto.contabilidad.repository;

import proyecto.contabilidad.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {}
