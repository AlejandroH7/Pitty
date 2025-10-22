package com.pitty.service;

import com.pitty.dto.evento.EventoCreateDTO;
import com.pitty.dto.evento.EventoResponseDTO;
import com.pitty.dto.evento.EventoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventoService {

    EventoResponseDTO create(EventoCreateDTO dto);

    EventoResponseDTO getById(Long id);

    Page<EventoResponseDTO> list(Pageable pageable);

    EventoResponseDTO update(Long id, EventoUpdateDTO dto);

    void delete(Long id);
}