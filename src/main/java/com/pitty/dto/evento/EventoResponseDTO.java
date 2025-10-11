package com.pitty.dto.evento;

import java.time.OffsetDateTime;

public record EventoResponseDTO(
        Long id,
        String titulo,
        String lugar,
        OffsetDateTime fechaHora,
        Long pedidoId,
        String pedidoCliente,
        String notas,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) { }
