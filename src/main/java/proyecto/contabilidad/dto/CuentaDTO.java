package proyecto.contabilidad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String tipo;
}
