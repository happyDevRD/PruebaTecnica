package com.happydev.accountmovementmanagementservice.controller;

import com.happydev.accountmovementmanagementservice.dto.EstadoCuentaDTO;
import com.happydev.accountmovementmanagementservice.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoCuentaDTO>> obtenerReporteEstadoCuenta(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam Long clienteId) {
        List<EstadoCuentaDTO> reportes = reporteService.generarReporteEstadoCuenta(fechaInicio, fechaFin, clienteId);
        return ResponseEntity.ok(reportes);
    }

}
