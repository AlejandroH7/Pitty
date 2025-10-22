package com.pitty.controller;

import com.pitty.dto.evento.EventoCreateDTO;
import com.pitty.dto.evento.EventoResponseDTO;
import com.pitty.dto.evento.EventoUpdateDTO;
import com.pitty.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Eventos", description = "Operaciones para gestionar eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento", description = "Registra un nuevo evento en el sistema.")
    public ResponseEntity<EventoResponseDTO> create(@Valid @RequestBody EventoCreateDTO createDTO) {
        EventoResponseDTO responseDTO = eventoService.create(createDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un evento por ID", description = "Recupera los detalles de un evento específico.")
    public ResponseEntity<EventoResponseDTO> getById(
            @Parameter(description = "ID del evento a obtener", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(eventoService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Lista eventos de forma paginada.")
    public ResponseEntity<Page<EventoResponseDTO>> list(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(eventoService.list(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento", description = "Actualiza todos los datos de un evento existente usando PUT.")
    public ResponseEntity<EventoResponseDTO> update(
            @Parameter(description = "ID del evento a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody EventoUpdateDTO updateDTO) {
        return ResponseEntity.ok(eventoService.update(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un evento", description = "Elimina un evento del sistema por su ID.")
    public void delete(
            @Parameter(description = "ID del evento a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        eventoService.delete(id);
    }
}