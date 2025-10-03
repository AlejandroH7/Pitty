package com.pitty.dto.pedido;

import java.math.BigDecimal;
import java.util.Map;

public class PedidoItemResponseDTO {
    private Long postreId;
    private String postreNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Map<String, Object> personalizaciones;

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
    public Map<String, Object> getPersonalizaciones() { return personalizaciones; }
    public void setPersonalizaciones(Map<String, Object> personalizaciones) { this.personalizaciones = personalizaciones; }
}