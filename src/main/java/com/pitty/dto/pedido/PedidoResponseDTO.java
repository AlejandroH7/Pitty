package com.pitty.dto.pedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoResponseDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private OffsetDateTime fechaEntrega;
    private String estado;
    private BigDecimal total;
    private String notas;
    private List<PedidoItemResponseDTO> items;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public OffsetDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(OffsetDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public List<PedidoItemResponseDTO> getItems() { return items; }
    public void setItems(List<PedidoItemResponseDTO> items) { this.items = items; }
}