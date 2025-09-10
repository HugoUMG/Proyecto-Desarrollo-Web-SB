package proyecto.contabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true)
  private String codigo; // 1.1.01

  @Column(nullable=false)
  private String nombre;

  @Column(nullable=false)
  private String tipo; // ACTIVO, PASIVO, PATRIMONIO, INGRESO, GASTO
}