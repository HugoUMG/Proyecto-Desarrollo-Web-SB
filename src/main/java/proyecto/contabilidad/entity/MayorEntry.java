package proyecto.contabilidad.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="mayor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MayorEntry {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="cuenta_id")
  private Cuenta cuenta;

  private LocalDate fecha;

  private String referencia; // id asiento o descripcion

  private BigDecimal debe = BigDecimal.ZERO;

  private BigDecimal haber = BigDecimal.ZERO;

  private BigDecimal saldo = BigDecimal.ZERO; // calculado
}
