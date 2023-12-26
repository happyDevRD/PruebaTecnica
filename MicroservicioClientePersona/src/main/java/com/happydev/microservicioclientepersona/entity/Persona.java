package com.happydev.microservicioclientepersona.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El género no puede estar vacío")
    @Column(name = "genero")
    private String genero;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor que 150")
    @Column(name = "edad")
    private Integer edad;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Size(max = 50, message = "La identificación no puede tener más de 50 caracteres")
    @Column(name = "identificacion")
    private String identificacion;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(name = "direccion")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "[0-9]+", message = "El teléfono solo debe contener números")
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 dígitos")
    @Column(name = "telefono")
    private String telefono;

}
