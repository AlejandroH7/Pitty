package com.pitty.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pitty.dto.ingrediente.IngredienteRequestDTO;
import com.pitty.dto.ingrediente.IngredienteResponseDTO;
import com.pitty.service.IngredienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    private final IngredienteService service;

    public IngredienteController(IngredienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crear(@Valid @RequestBody IngredienteRequestDTO dto) {
        var creado = service.crear(dto);
        return ResponseEntity
            .created(URI.create("/api/ingredientes/" + creado.getId()))
            .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(@PathVariable Long id,
                                                             @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}