package com.happydev.accountmovementmanagementservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteDTO {
    private Long id;
    private String nombre;

    public ClienteDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}
