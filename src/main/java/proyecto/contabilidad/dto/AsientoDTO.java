package proyecto.contabilidad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoDTO {
    private LocalDate fecha;
    private String descripcion;
    private List<MovimientoDTO> movimientos;
}
