package com.pitty.mapper;

import com.pitty.domain.Evento;
import com.pitty.domain.Pedido;
import com.pitty.dto.evento.EventoRequestDTO;
import com.pitty.dto.evento.EventoResponseDTO;

public final class EventoMapper {

    private EventoMapper() {}

    public static Evento toEntity(EventoRequestDTO dto, Pedido pedido) {
        var entity = new Evento();
        entity.setTitulo(dto.titulo());
        entity.setLugar(dto.lugar());
        entity.setFechaHora(dto.fechaHora());
        entity.setPedido(pedido);
        entity.setNotas(dto.notas());
        return entity;
    }

    public static void copy(EventoRequestDTO dto, Evento entity, Pedido pedido) {
        entity.setTitulo(dto.titulo());
        entity.setLugar(dto.lugar());
        entity.setFechaHora(dto.fechaHora());
        entity.setPedido(pedido);
        entity.setNotas(dto.notas());
    }

    public static EventoResponseDTO toResponse(Evento entity) {
        var pedido = entity.getPedido();
        return new EventoResponseDTO(
                entity.getId(),
                entity.getTitulo(),
                entity.getLugar(),
                entity.getFechaHora(),
                pedido != null ? pedido.getId() : null,
                pedido != null && pedido.getCliente() != null ? pedido.getCliente().getNombre() : null,
                entity.getNotas(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
