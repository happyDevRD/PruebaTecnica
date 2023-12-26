package com.happydev.accountmovementmanagementservice.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de cuenta no puede estar vacío")
    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta no puede estar vacío")
    @Column(name = "tipo")
    private String tipo;

    @NotNull(message = "El saldo inicial no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El saldo inicial debe ser mayor que 0")
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCuenta estado;

    @Column(name = "cliente_id")
    private Long clienteId;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Movimiento> movimientos;

}
