package com.pitty.service.impl;

import com.pitty.domain.Evento;
import com.pitty.dto.evento.EventoCreateDTO;
import com.pitty.dto.evento.EventoResponseDTO;
import com.pitty.dto.evento.EventoUpdateDTO;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.EventoMapper;
import com.pitty.repository.EventoRepository;
import com.pitty.service.EventoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    public EventoServiceImpl(EventoRepository eventoRepository, EventoMapper eventoMapper) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
    }

    @Override
    @Transactional
    public EventoResponseDTO create(EventoCreateDTO dto) {
        Evento evento = eventoMapper.toEntity(dto);
        evento.setCreatedAt(OffsetDateTime.now());
        evento.setUpdatedAt(OffsetDateTime.now()); // Set updatedAt on creation
        Evento savedEvento = eventoRepository.save(evento);
        return eventoMapper.toResponse(savedEvento);
    }

    @Override
    @Transactional(readOnly = true)
    public EventoResponseDTO getById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado con ID: " + id));
        return eventoMapper.toResponse(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventoResponseDTO> list(Pageable pageable) {
        return eventoRepository.findAll(pageable).map(eventoMapper::toResponse);
    }

    @Override
    @Transactional
    public EventoResponseDTO update(Long id, EventoUpdateDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado con ID: " + id));
        eventoMapper.applyUpdate(evento, dto);
        Evento updatedEvento = eventoRepository.save(evento);
        return eventoMapper.toResponse(updatedEvento);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new NotFoundException("Evento no encontrado con ID: " + id);
        }
        eventoRepository.deleteById(id);
    }
}