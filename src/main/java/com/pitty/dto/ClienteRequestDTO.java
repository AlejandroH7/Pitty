package com.pitty.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private String telefono;
    private String notas;
}