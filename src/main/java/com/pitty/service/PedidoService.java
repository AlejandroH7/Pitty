package com.pitty.service;

import java.time.LocalDate;
import java.util.List;

import com.pitty.dto.PedidoCreateDTO;
import com.pitty.dto.PedidoEstadoUpdateDTO;
import com.pitty.dto.PedidoResponseDTO;

public interface PedidoService {

    PedidoResponseDTO crear(PedidoCreateDTO dto);

    PedidoResponseDTO obtenerPorId(Long id);

    // filtros opcionales (puedes ignorarlos dentro de la impl por ahora)
    List<PedidoResponseDTO> listar(String q, LocalDate desde, LocalDate hasta);

    PedidoResponseDTO updateEstado(Long id, PedidoEstadoUpdateDTO dto);

    void eliminar(Long id);
}