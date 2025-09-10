package proyecto.contabilidad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EstadoResultadosDTO {
    private BigDecimal ingresos;
    private BigDecimal gastos;
    private BigDecimal utilidad;
}
