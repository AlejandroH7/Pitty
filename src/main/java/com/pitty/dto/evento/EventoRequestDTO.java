package com.pitty.dto.evento;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record EventoRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150, message = "El título no puede superar 150 caracteres")
        String titulo,

        @Size(max = 150, message = "El lugar no puede superar 150 caracteres")
        String lugar,

        @NotNull(message = "La fecha/hora es obligatoria")
        @FutureOrPresent(message = "La fecha/hora debe ser presente o futura")
        OffsetDateTime fechaHora,

        Long pedidoId,

        @Size(max = 500, message = "Las notas no pueden superar 500 caracteres")
        String notas
) { }
