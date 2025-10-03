package com.pitty.service;

import com.pitty.dto.receta.RecetaDetalleRequestDTO;
import com.pitty.dto.receta.RecetaResponseDTO;

import java.util.List;

public interface RecetaService {

    RecetaResponseDTO getByPostre(Long postreId);

    /** Reemplaza toda la receta del postre por los items provistos */
    RecetaResponseDTO replaceRecipe(Long postreId, List<RecetaDetalleRequestDTO> items);

    /** Crea o actualiza un item (ingrediente) en la receta del postre */
    RecetaResponseDTO addOrUpdateItem(Long postreId, RecetaDetalleRequestDTO item);

    /** Elimina un ingrediente de la receta del postre */
    void deleteItem(Long postreId, Long ingredienteId);
}