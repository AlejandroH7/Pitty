package com.pitty.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.JsonNode;

public class PedidoItemResponseDTO {
    private Long postreId;
    private String postreNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private JsonNode personalizaciones;

    public Long getPostreId() { return postreId; }
    public void setPostreId(Long postreId) { this.postreId = postreId; }

    public String getPostreNombre() { return postreNombre; }
    public void setPostreNombre(String postreNombre) { this.postreNombre = postreNombre; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public JsonNode getPersonalizaciones() { return personalizaciones; }
    public void setPersonalizaciones(JsonNode personalizaciones) { this.personalizaciones = personalizaciones; }
}