package com.pitty.mapper;

import com.pitty.domain.Ingrediente;
import com.pitty.dto.ingrediente.IngredienteRequestDTO;
import com.pitty.dto.ingrediente.IngredienteResponseDTO;

public class IngredienteMapper {

    public static Ingrediente toEntity(IngredienteRequestDTO dto) {
        var e = new Ingrediente();
        e.setNombre(dto.getNombre());
        e.setUnidad(dto.getUnidad());
        e.setStockMinimo(dto.getStockMinimo());
        e.setStockActual(dto.getStockActual());
        e.setActivo(dto.getActivo() != null ? dto.getActivo() : Boolean.TRUE);
        return e;
    }

    public static void copyToEntity(IngredienteRequestDTO dto, Ingrediente e) {
        e.setNombre(dto.getNombre());
        e.setUnidad(dto.getUnidad());
        e.setStockMinimo(dto.getStockMinimo());
        e.setStockActual(dto.getStockActual());
        e.setActivo(dto.getActivo() != null ? dto.getActivo() : e.getActivo());
    }

    public static IngredienteResponseDTO toResponse(Ingrediente e) {
        var r = new IngredienteResponseDTO();
        r.setId(e.getId());
        r.setNombre(e.getNombre());
        r.setUnidad(e.getUnidad());
        r.setStockMinimo(e.getStockMinimo());
        r.setStockActual(e.getStockActual());
        r.setActivo(e.getActivo());
        r.setCreatedAt(e.getCreatedAt());
        r.setUpdatedAt(e.getUpdatedAt());
        return r;
    }
}