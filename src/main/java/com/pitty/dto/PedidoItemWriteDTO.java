package com.pitty.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class PedidoItemWriteDTO {
    @NotNull
    private Long postreId;

    @NotNull
    @Min(1)
    private Integer cantidad;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precioUnitario;

    private JsonNode personalizaciones;
}
