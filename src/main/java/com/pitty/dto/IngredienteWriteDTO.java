package com.pitty.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredienteWriteDTO {
    @NotBlank
    @Size(max = 120)
    private String nombre;

    @NotBlank
    @Size(max = 20)
    private String unidad;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal stockActual;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal stockMinimo;

    private Boolean activo = Boolean.TRUE;

    @Size(max = 120)
    private String createdBy;

    @Size(max = 120)
    private String updatedBy;
}
