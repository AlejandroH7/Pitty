package com.pitty.dto.pedido;

import jakarta.validation.constraints.NotBlank;

public class PedidoEstadoUpdateDTO {
    @NotBlank
    private String estado; // BORRADOR, CONFIRMADO, ENTREGADO, CANCELADO

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}