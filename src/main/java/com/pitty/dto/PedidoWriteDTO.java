package com.pitty.dto;

import com.pitty.domain.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoWriteDTO {
    @NotNull
    private Long clienteId;

    @NotNull
    private OffsetDateTime fechaEntrega;

    private Pedido.Estado estado;

    private String notas;

    @Valid
    private List<PedidoItemWriteDTO> items;

    private String createdBy;

    private String updatedBy;
}
