package com.pitty.dto;

import com.pitty.domain.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoReadDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private OffsetDateTime fechaEntrega;
    private Pedido.Estado estado;
    private String notas;
    private BigDecimal total;
    private List<PedidoItemReadDTO> items;
    private String createdBy;
    private String updatedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
