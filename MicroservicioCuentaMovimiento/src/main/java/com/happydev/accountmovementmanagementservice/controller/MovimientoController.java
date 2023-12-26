package com.happydev.accountmovementmanagementservice.controller;

import com.happydev.accountmovementmanagementservice.entity.Movimiento;
import com.happydev.accountmovementmanagementservice.service.MovimientoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }
    @PostMapping
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody Movimiento movimiento) {
        return new ResponseEntity<>(movimientoService.registrarMovimiento(movimiento), HttpStatus.CREATED);
    }
    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<Movimiento>> obtenerMovimientosPorCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(movimientoService.obtenerMovimientosPorCuenta(cuentaId));
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> obtenerMovimientosPorRangoDeFechas(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        return ResponseEntity.ok(movimientoService.obtenerMovimientosPorRangoDeFechas(fechaInicio, fechaFin));
    }
}
