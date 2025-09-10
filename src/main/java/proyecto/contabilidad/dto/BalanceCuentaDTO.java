// dto/BalanceCuentaDTO.java
package proyecto.contabilidad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceCuentaDTO {
    private String codigo;
    private String nombre;
    private BigDecimal debe;
    private BigDecimal haber;
    private BigDecimal saldo;
    private String tipo; // ACTIVO, PASIVO, etc.
}
