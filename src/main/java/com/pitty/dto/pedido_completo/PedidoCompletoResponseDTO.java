package com.pitty.dto.pedido_completo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PedidoCompletoResponseDTO {

    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private Long postreId;
    private String postreNombre;
    private String nota;
    private Integer cantidad;
    private BigDecimal total;
    private OffsetDateTime fechaEntrega;
    private OffsetDateTime createdAt;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getPostreId() {
        return postreId;
    }

    public void setPostreId(Long postreId) {
        this.postreId = postreId;
    }

    public String getPostreNombre() {
        return postreNombre;
    }

    public void setPostreNombre(String postreNombre) {
        this.postreNombre = postreNombre;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OffsetDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(OffsetDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
