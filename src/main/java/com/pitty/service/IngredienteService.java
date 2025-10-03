package com.pitty.service;

import java.util.List;

import com.pitty.dto.ingrediente.IngredienteRequestDTO;
import com.pitty.dto.ingrediente.IngredienteResponseDTO;

public interface IngredienteService {
    List<IngredienteResponseDTO> listar();
    IngredienteResponseDTO obtener(Long id);
    IngredienteResponseDTO crear(IngredienteRequestDTO dto);
    IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto);
    void eliminar(Long id);
}