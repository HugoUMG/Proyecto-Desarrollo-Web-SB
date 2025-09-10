package proyecto.contabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique=true, nullable=false)
  private String username;

  @Column(nullable=false)
  private String password; // bcrypt

  @ManyToOne
  @JoinColumn(name="rol_id")
  private Rol rol;
}