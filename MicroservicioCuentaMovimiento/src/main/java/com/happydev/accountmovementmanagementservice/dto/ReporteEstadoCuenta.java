package com.happydev.accountmovementmanagementservice.dto;

import com.happydev.accountmovementmanagementservice.entity.Cuenta;
import com.happydev.accountmovementmanagementservice.entity.Movimiento;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReporteEstadoCuenta {
    private List<Cuenta> cuentas;
    private List<Movimiento> movimientos;

    public ReporteEstadoCuenta(List<Cuenta> cuentas, List<Movimiento> movimientos) {
        this.cuentas = cuentas;
        this.movimientos = movimientos;
    }

}
