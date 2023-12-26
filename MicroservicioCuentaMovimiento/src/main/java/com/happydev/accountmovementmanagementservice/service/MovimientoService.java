package com.happydev.accountmovementmanagementservice.service;

import com.happydev.accountmovementmanagementservice.entity.Cuenta;
import com.happydev.accountmovementmanagementservice.entity.Movimiento;
import com.happydev.accountmovementmanagementservice.entity.TipoMovimiento;
import com.happydev.accountmovementmanagementservice.exception.CustomEntityNotFoundException;
import com.happydev.accountmovementmanagementservice.exception.SaldoInsuficienteException;
import com.happydev.accountmovementmanagementservice.repository.CuentaRepository;
import com.happydev.accountmovementmanagementservice.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Movimiento registrarMovimiento(Movimiento movimiento) {
        validarMovimiento(movimiento);
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new CustomEntityNotFoundException("Cuenta no encontrada con ID: " + movimiento.getCuenta().getId()));

        actualizarSaldoCuenta(cuenta, movimiento);
        movimiento.setCuenta(cuenta);
        return movimientoRepository.save(movimiento);
    }


    private void validarMovimiento(Movimiento movimiento) {
        if (movimiento.getCuenta() == null || movimiento.getValor() == null) {
            throw new IllegalArgumentException("El movimiento debe tener cuenta y valor definidos");
        }

        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new CustomEntityNotFoundException("Cuenta no encontrada con ID: " + movimiento.getCuenta().getId()));

        if (movimiento.getTipoMovimiento().equals(TipoMovimiento.RETIRO) && cuenta.getSaldoInicial().compareTo(movimiento.getValor()) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }
    }

    private void actualizarSaldoCuenta(Cuenta cuenta, Movimiento movimiento) {
        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal valorMovimiento = movimiento.getValor();
        cuenta.setSaldoInicial(movimiento.getTipoMovimiento().equals(TipoMovimiento.DEPOSITO)
                ? saldoActual.add(valorMovimiento) : saldoActual.subtract(valorMovimiento));
        cuentaRepository.save(cuenta);
    }

    public List<Movimiento> obtenerMovimientosPorCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId);
    }

    public List<Movimiento> obtenerMovimientosPorRangoDeFechas(Date fechaInicio, Date fechaFin) {
        return movimientoRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}
