package com.happydev.accountmovementmanagementservice.controller;


import com.happydev.accountmovementmanagementservice.entity.Cuenta;
import com.happydev.accountmovementmanagementservice.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta) {
        return new ResponseEntity<>(cuentaService.crearOActualizarCuenta(cuenta), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodasLasCuentas() {
        return ResponseEntity.ok(cuentaService.obtenerTodasLasCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        cuenta.setId(id);
        return ResponseEntity.ok(cuentaService.actualizarCuenta(cuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/crearParaCliente/{clienteId}")
    public ResponseEntity<Cuenta> crearCuentaParaCliente(@Valid @RequestBody Cuenta cuenta, @PathVariable Long clienteId) {
        Cuenta nuevaCuenta = cuentaService.crearCuentaParaCliente(cuenta, clienteId);
        return ResponseEntity.ok(nuevaCuenta);
    }
}
