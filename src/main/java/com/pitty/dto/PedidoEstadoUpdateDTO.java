package com.pitty.dto;

import jakarta.validation.constraints.NotBlank;

public class PedidoEstadoUpdateDTO {
    @NotBlank(message = "El estado es obligatorio")
    private String estado; // BORRADOR, CONFIRMADO, ENTREGADO, CANCELADO

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
