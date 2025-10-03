// src/main/java/com/pitty/dto/postre/PostreResponseDTO.java
package com.pitty.dto.postre;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PostreResponseDTO(
        Long id,
        String nombre,
        BigDecimal precio,
        Integer porciones,
        Boolean activo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) { }