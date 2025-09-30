package com.pitty.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoItemCreateDTO {
    private Long postreId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    // JSONB -> JsonNode
    private JsonNode personalizaciones;
}
