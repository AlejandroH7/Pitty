package com.pitty.dto;

public class PedidoEstadoUpdateDTO {
    // valores esperados: BORRADOR, CONFIRMADO, ENTREGADO, CANCELADO (o los que tengas)
    private String estado;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}