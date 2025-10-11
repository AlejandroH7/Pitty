package com.pitty.service;

import com.pitty.dto.evento.EventoRequestDTO;
import com.pitty.dto.evento.EventoResponseDTO;

import java.util.List;

public interface EventoService {

    EventoResponseDTO crear(EventoRequestDTO dto);

    EventoResponseDTO obtener(Long id);

    List<EventoResponseDTO> listar();

    EventoResponseDTO actualizar(Long id, EventoRequestDTO dto);

    void eliminar(Long id);
}
