package com.pitty.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;

public class PedidoCreateDTO {
    private Long clienteId;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private OffsetDateTime fechaEntrega;

    @Size(max = 500, message = "Las notas no deben superar 500 caracteres")
    private String notas;

    @NotEmpty(message = "El pedido debe contener al menos un ítem")
    @Valid
    private List<PedidoItemCreateDTO> items;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public OffsetDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(OffsetDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public List<PedidoItemCreateDTO> getItems() { return items; }
    public void setItems(List<PedidoItemCreateDTO> items) { this.items = items; }
}
