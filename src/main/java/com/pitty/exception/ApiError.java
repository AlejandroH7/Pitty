// src/main/java/com/pitty/exception/ApiError.java
package com.pitty.exception;

import java.time.OffsetDateTime;

/**
 * Estructura de error estándar para las respuestas.
 * Compatible con GlobalExceptionHandler.body(...).
 */
public record ApiError(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
