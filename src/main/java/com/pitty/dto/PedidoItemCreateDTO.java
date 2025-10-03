package com.pitty.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PedidoItemCreateDTO {
    private Long postreId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private Map<String, Object> personalizaciones;

    public Long getPostreId() { return postreId; }
    public void setPostreId(Long postreId) { this.postreId = postreId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Map<String, Object> getPersonalizaciones() { return personalizaciones; }
    public void setPersonalizaciones(Map<String, Object> personalizaciones) { this.personalizaciones = personalizaciones; }
}