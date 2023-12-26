package com.happydev.accountmovementmanagementservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EstadoCuentaDTO {
    private Long cuentaId;
    private BigDecimal saldoActual;
    private List<MovimientoDTO> movimientos;

    
}
