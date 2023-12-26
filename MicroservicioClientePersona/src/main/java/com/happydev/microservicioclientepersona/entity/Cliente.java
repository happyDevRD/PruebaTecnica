package com.happydev.microservicioclientepersona.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@DiscriminatorValue("Cliente")
public class Cliente extends Persona {

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$", message = "La contraseña debe contener al menos 6 caracteres, incluyendo letras y números")
    @Column(name = "contraseña")
    private String contraseña;

    @NotNull(message = "El estado no puede ser nulo")
    @Column(name = "estado")
    private Boolean estado;

}
