package com.pitty.mapper;

import com.pitty.domain.Evento;
import com.pitty.dto.evento.EventoCreateDTO;
import com.pitty.dto.evento.EventoResponseDTO;
import com.pitty.dto.evento.EventoUpdateDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class EventoMapper {

    public Evento toEntity(EventoCreateDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setNombre(dto.getNombre());
        evento.setFecha(dto.getFecha());
        evento.setDescripcion(dto.getDescripcion());
        evento.setUbicacion(dto.getUbicacion());
        // createdAt will be set by service or DB default
        return evento;
    }

    public void applyUpdate(Evento entity, EventoUpdateDTO dto) {
        entity.setTitulo(dto.getTitulo());
        entity.setNombre(dto.getNombre());
        entity.setFecha(dto.getFecha());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUbicacion(dto.getUbicacion());
        entity.setUpdatedAt(OffsetDateTime.now());
    }

    public EventoResponseDTO toResponse(Evento entity) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setNombre(entity.getNombre());
        dto.setFecha(entity.getFecha());
        dto.setDescripcion(entity.getDescripcion());
        dto.setUbicacion(entity.getUbicacion());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}