package proyecto.contabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name="movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="asiento_id")
  private Asiento asiento;

  @ManyToOne
  @JoinColumn(name="cuenta_id")
  private Cuenta cuenta;

  @Column(nullable=false)
  private BigDecimal debe = BigDecimal.ZERO;

  @Column(nullable=false)
  private BigDecimal haber = BigDecimal.ZERO;
}
