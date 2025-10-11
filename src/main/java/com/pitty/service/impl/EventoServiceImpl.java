package com.pitty.service.impl;

import com.pitty.domain.Evento;
import com.pitty.domain.Pedido;
import com.pitty.dto.evento.EventoRequestDTO;
import com.pitty.dto.evento.EventoResponseDTO;
import com.pitty.exception.ConflictException;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.EventoMapper;
import com.pitty.repository.EventoRepository;
import com.pitty.repository.PedidoRepository;
import com.pitty.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public EventoResponseDTO crear(EventoRequestDTO dto) {
        Pedido pedido = resolvePedido(dto.pedidoId());
        Evento entity = EventoMapper.toEntity(dto, pedido);
        Evento saved = eventoRepository.save(entity);
        return EventoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EventoResponseDTO obtener(Long id) {
        Evento entity = eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado: id=" + id));
        return EventoMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoResponseDTO> listar() {
        return eventoRepository.findAll()
                .stream()
                .map(EventoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public EventoResponseDTO actualizar(Long id, EventoRequestDTO dto) {
        Evento entity = eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado: id=" + id));
        Pedido pedido = resolvePedido(dto.pedidoId());
        EventoMapper.copy(dto, entity, pedido);
        Evento updated = eventoRepository.save(entity);
        return EventoMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new NotFoundException("Evento no encontrado: id=" + id);
        }
        eventoRepository.deleteById(id);
    }

    private Pedido resolvePedido(Long pedidoId) {
        if (pedidoId == null) {
            return null;
        }
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: id=" + pedidoId));
        if (pedido.getEstado() == Pedido.Estado.CANCELADO) {
            throw new ConflictException("No se puede asociar un evento a un pedido cancelado");
        }
        return pedido;
    }
}
