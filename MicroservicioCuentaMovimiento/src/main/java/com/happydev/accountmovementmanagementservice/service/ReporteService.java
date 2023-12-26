package com.happydev.accountmovementmanagementservice.service;

import com.happydev.accountmovementmanagementservice.dto.EstadoCuentaDTO;
import com.happydev.accountmovementmanagementservice.dto.MovimientoDTO;
import com.happydev.accountmovementmanagementservice.entity.Cuenta;
import com.happydev.accountmovementmanagementservice.repository.CuentaRepository;
import com.happydev.accountmovementmanagementservice.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public ReporteService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public List<EstadoCuentaDTO> generarReporteEstadoCuenta(Date fechaInicio, Date fechaFin, Long clienteId) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        List<EstadoCuentaDTO> reportes = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            EstadoCuentaDTO reporte = new EstadoCuentaDTO();
            reporte.setCuentaId(cuenta.getId());
            reporte.setSaldoActual(cuenta.getSaldoInicial());
            reporte.setMovimientos(
                    movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicio, fechaFin)
                            .stream()
                            .map(movimiento -> new MovimientoDTO(movimiento.getFecha(), movimiento.getTipoMovimiento(), movimiento.getValor()))
                            .collect(Collectors.toList())
            );
            reportes.add(reporte);
        }

        return reportes;
    }
}
