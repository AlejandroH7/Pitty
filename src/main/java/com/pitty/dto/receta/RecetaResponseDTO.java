package com.pitty.dto.receta;

import java.math.BigDecimal;
import java.util.List;

public class RecetaResponseDTO {

    private Long postreId;
    private String postreNombre;
    private List<Item> items;

    public Long getPostreId() { return postreId; }
    public void setPostreId(Long postreId) { this.postreId = postreId; }

    public String getPostreNombre() { return postreNombre; }
    public void setPostreNombre(String postreNombre) { this.postreNombre = postreNombre; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public static class Item {
        private Long ingredienteId;
        private String ingredienteNombre;
        private String unidad;
        private BigDecimal cantidadPorPostre;
        private BigDecimal mermaPct;

        public Long getIngredienteId() { return ingredienteId; }
        public void setIngredienteId(Long ingredienteId) { this.ingredienteId = ingredienteId; }

        public String getIngredienteNombre() { return ingredienteNombre; }
        public void setIngredienteNombre(String ingredienteNombre) { this.ingredienteNombre = ingredienteNombre; }

        public String getUnidad() { return unidad; }
        public void setUnidad(String unidad) { this.unidad = unidad; }

        public BigDecimal getCantidadPorPostre() { return cantidadPorPostre; }
        public void setCantidadPorPostre(BigDecimal cantidadPorPostre) { this.cantidadPorPostre = cantidadPorPostre; }

        public BigDecimal getMermaPct() { return mermaPct; }
        public void setMermaPct(BigDecimal mermaPct) { this.mermaPct = mermaPct; }
    }
}