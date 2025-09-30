package com.pitty.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoCreateDTO {
    private Long clienteId;
    private OffsetDateTime fechaEntrega;
    private String notas;
    private List<PedidoItemCreateDTO> items; // usa el DTO de arriba
}
