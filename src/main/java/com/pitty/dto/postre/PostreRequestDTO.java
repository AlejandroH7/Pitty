// src/main/java/com/pitty/dto/postre/PostreRequestDTO.java
package com.pitty.dto.postre;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PostreRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "Las porciones son obligatorias")
        @Min(value = 1, message = "Las porciones deben ser al menos 1")
        Integer porciones,

        @NotNull(message = "El estado 'activo' es obligatorio")
        Boolean activo
) { }
