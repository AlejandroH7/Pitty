package com.pitty.dto.receta;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class RecetaDetalleRequestDTO {

    @NotNull(message = "ingredienteId es obligatorio")
    private Long ingredienteId;

    @NotNull(message = "cantidadPorPostre es obligatoria")
    @DecimalMin(value = "0.0001", message = "cantidadPorPostre debe ser > 0")
    private BigDecimal cantidadPorPostre;

    // Porcentaje 0..100
    @NotNull(message = "mermaPct es obligatorio")
    @DecimalMin(value = "0", message = "mermaPct no puede ser negativo")
    @DecimalMax(value = "100", message = "mermaPct no puede ser mayor a 100")
    private BigDecimal mermaPct;

    public Long getIngredienteId() { return ingredienteId; }
    public void setIngredienteId(Long ingredienteId) { this.ingredienteId = ingredienteId; }

    public BigDecimal getCantidadPorPostre() { return cantidadPorPostre; }
    public void setCantidadPorPostre(BigDecimal cantidadPorPostre) { this.cantidadPorPostre = cantidadPorPostre; }

    public BigDecimal getMermaPct() { return mermaPct; }
    public void setMermaPct(BigDecimal mermaPct) { this.mermaPct = mermaPct; }
}