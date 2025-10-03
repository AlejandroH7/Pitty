// src/main/java/com/pitty/mapper/PostreMapper.java
package com.pitty.mapper;

import com.pitty.domain.Postre;
import com.pitty.dto.postre.PostreRequestDTO;
import com.pitty.dto.postre.PostreResponseDTO;

public class PostreMapper {

    private PostreMapper() {}

    public static Postre toEntity(PostreRequestDTO dto) {
        var p = new Postre();
        p.setNombre(dto.nombre().trim());
        p.setPrecio(dto.precio());
        p.setPorciones(dto.porciones());
        p.setActivo(dto.activo());
        return p;
    }

    public static void copy(PostreRequestDTO dto, Postre entity) {
        entity.setNombre(dto.nombre().trim());
        entity.setPrecio(dto.precio());
        entity.setPorciones(dto.porciones());
        entity.setActivo(dto.activo());
    }

    public static PostreResponseDTO toResponse(Postre entity) {
        return new PostreResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getPrecio(),
                entity.getPorciones(),
                entity.getActivo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}