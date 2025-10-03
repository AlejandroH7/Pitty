package com.pitty.controller;

import com.pitty.dto.receta.RecetaDetalleRequestDTO;
import com.pitty.dto.receta.RecetaResponseDTO;
import com.pitty.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/postres/{postreId}/receta")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    // GET receta del postre
    @GetMapping
    public ResponseEntity<RecetaResponseDTO> get(@PathVariable Long postreId) {
        var body = recetaService.getByPostre(postreId);
        return ResponseEntity.ok(body);
    }

    // Reemplaza toda la receta
    @PutMapping
    public ResponseEntity<RecetaResponseDTO> replace(
            @PathVariable Long postreId,
            @RequestBody @Valid List<RecetaDetalleRequestDTO> items) {
        var body = recetaService.replaceRecipe(postreId, items);
        return ResponseEntity.ok(body);
    }

    // Agrega o actualiza un item
    @PostMapping("/items")
    public ResponseEntity<RecetaResponseDTO> addOrUpdateItem(
            @PathVariable Long postreId,
            @RequestBody @Valid RecetaDetalleRequestDTO item) {
        var body = recetaService.addOrUpdateItem(postreId, item);
        return ResponseEntity.created(URI.create("/api/postres/" + postreId + "/receta")).body(body);
    }

    // Elimina un item
    @DeleteMapping("/items/{ingredienteId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long postreId, @PathVariable Long ingredienteId) {
        recetaService.deleteItem(postreId, ingredienteId);
        return ResponseEntity.noContent().build();
    }
}