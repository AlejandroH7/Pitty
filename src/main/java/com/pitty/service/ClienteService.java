package com.pitty.service;

import com.pitty.dto.ClienteRequestDTO;
import com.pitty.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO crear(ClienteRequestDTO dto);
    List<ClienteResponseDTO> listar();
    ClienteResponseDTO buscarPorId(Long id);
    ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto);
    void eliminar(Long id);
}