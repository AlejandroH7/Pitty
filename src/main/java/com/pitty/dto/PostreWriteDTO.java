package com.pitty.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
public class PostreWriteDTO {
    @NotBlank
    @Size(max = 120)
    private String nombre;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precio;

    @NotNull
    @Min(1)
    private Integer porciones;

    private Boolean activo = Boolean.TRUE;

    @Size(max = 120)
    private String createdBy;

    @Size(max = 120)
    private String updatedBy;
}
