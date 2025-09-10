package proyecto.contabilidad.service;

import proyecto.contabilidad.dto.AsientoDTO;
import proyecto.contabilidad.dto.MovimientoDTO;
import proyecto.contabilidad.entity.*;
import proyecto.contabilidad.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class AsientoService {
    private final AsientoRepository asientoRepo;
    private final MovimientoRepository movimientoRepo;
    private final CuentaRepository cuentaRepo;
    private final MayorEntryRepository mayorRepo;

    public AsientoService(AsientoRepository asientoRepo,
                          MovimientoRepository movimientoRepo,
                          CuentaRepository cuentaRepo,
                          MayorEntryRepository mayorRepo) {
        this.asientoRepo = asientoRepo;
        this.movimientoRepo = movimientoRepo;
        this.cuentaRepo = cuentaRepo;
        this.mayorRepo = mayorRepo;
    }

    @Transactional
    public Asiento registrarAsiento(AsientoDTO dto) {
        BigDecimal sumaDebe = dto.getMovimientos().stream()
                .map(m -> m.getDebe() != null ? m.getDebe() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sumaHaber = dto.getMovimientos().stream()
                .map(m -> m.getHaber() != null ? m.getHaber() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sumaDebe.compareTo(sumaHaber) != 0) {
            throw new IllegalArgumentException("Asiento desequilibrado: debe != haber");
        }

        Asiento asiento = new Asiento();
        asiento.setFecha(dto.getFecha());
        asiento.setDescripcion(dto.getDescripcion());
        asiento = asientoRepo.save(asiento);

        for (MovimientoDTO mDto : dto.getMovimientos()) {
            Cuenta c = cuentaRepo.findById(mDto.getCuentaId())
                    .orElseThrow(() -> new IllegalArgumentException("Cuenta no existe: " + mDto.getCuentaId()));

            Movimiento m = new Movimiento();
            m.setAsiento(asiento);
            m.setCuenta(c);
            m.setDebe(mDto.getDebe() != null ? mDto.getDebe() : BigDecimal.ZERO);
            m.setHaber(mDto.getHaber() != null ? mDto.getHaber() : BigDecimal.ZERO);
            movimientoRepo.save(m);

            MayorEntry me = new MayorEntry();
            me.setCuenta(c);
            me.setFecha(dto.getFecha());
            me.setReferencia("ASIENTO#" + asiento.getId());
            me.setDebe(m.getDebe());
            me.setHaber(m.getHaber());

            BigDecimal ultimoSaldo = mayorRepo.findTopByCuentaOrderByFechaDesc(c)
                    .map(MayorEntry::getSaldo).orElse(BigDecimal.ZERO);

            BigDecimal nuevoSaldo = ultimoSaldo.add(me.getDebe().subtract(me.getHaber()));
            me.setSaldo(nuevoSaldo);
            mayorRepo.save(me);
        }

        return asiento;
    }
}
