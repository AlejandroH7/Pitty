package com.pitty.dto;

import java.time.OffsetDateTime;
import java.util.List;

public class PedidoCreateDTO {
    private Long clienteId;
    private OffsetDateTime fechaEntrega;
    private String notas;
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