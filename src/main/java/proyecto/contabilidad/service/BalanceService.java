package proyecto.contabilidad.service;

import proyecto.contabilidad.dto.BalanceCuentaDTO;
import proyecto.contabilidad.dto.EstadoResultadosDTO;
import proyecto.contabilidad.entity.Cuenta;
import proyecto.contabilidad.entity.MayorEntry;
import proyecto.contabilidad.repository.CuentaRepository;
import proyecto.contabilidad.repository.MayorEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    private final CuentaRepository cuentaRepo;
    private final MayorEntryRepository mayorRepo;

    public BalanceService(CuentaRepository cuentaRepo, MayorEntryRepository mayorRepo) {
        this.cuentaRepo = cuentaRepo;
        this.mayorRepo = mayorRepo;
    }

    // Balance de comprobación
    public List<BalanceCuentaDTO> generarBalanceComprobacion() {
        List<Cuenta> cuentas = cuentaRepo.findAll();

        return cuentas.stream().map(c -> {
            List<MayorEntry> movimientos = mayorRepo.findAll()
                    .stream().filter(m -> m.getCuenta().getId().equals(c.getId()))
                    .toList();

            BigDecimal debe = movimientos.stream()
                    .map(MayorEntry::getDebe)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal haber = movimientos.stream()
                    .map(MayorEntry::getHaber)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saldo = movimientos.stream()
                    .map(MayorEntry::getSaldo)
                    .reduce(BigDecimal.ZERO, (a, b) -> b); // último saldo

            return new BalanceCuentaDTO(
                    c.getCodigo(), c.getNombre(),
                    debe, haber, saldo, c.getTipo()
            );
        }).collect(Collectors.toList());
    }

    // Balance General (Activos - Pasivos - Patrimonio)
    public Map<String, List<BalanceCuentaDTO>> generarBalanceGeneral() {
        List<BalanceCuentaDTO> balance = generarBalanceComprobacion();
        return balance.stream().collect(Collectors.groupingBy(BalanceCuentaDTO::getTipo));
    }

    // Estado de Resultados
    public EstadoResultadosDTO generarEstadoResultados() {
        List<BalanceCuentaDTO> balance = generarBalanceComprobacion();

        BigDecimal ingresos = balance.stream()
                .filter(c -> c.getTipo().equalsIgnoreCase("INGRESO"))
                .map(BalanceCuentaDTO::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal gastos = balance.stream()
                .filter(c -> c.getTipo().equalsIgnoreCase("GASTO"))
                .map(BalanceCuentaDTO::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal utilidad = ingresos.subtract(gastos);

        return new EstadoResultadosDTO(ingresos, gastos, utilidad);
    }
}
