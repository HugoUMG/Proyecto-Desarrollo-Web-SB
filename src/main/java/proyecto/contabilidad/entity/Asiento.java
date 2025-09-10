package proyecto.contabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="asientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asiento {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate fecha;

  private String descripcion;

  @OneToMany(mappedBy = "asiento", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Movimiento> movimientos;
}