package com.happydev.accountmovementmanagementservice.dto;

import com.happydev.accountmovementmanagementservice.entity.TipoMovimiento;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MovimientoDTO {
    private Date fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;

    public MovimientoDTO(Date fecha, TipoMovimiento tipoMovimiento, BigDecimal valor) {
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
    }
}
