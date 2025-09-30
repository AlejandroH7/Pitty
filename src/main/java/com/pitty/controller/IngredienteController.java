package com.pitty.controller;

import com.pitty.dto.IngredienteReadDTO;
import com.pitty.dto.IngredienteWriteDTO;
import com.pitty.service.IngredienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

  private final IngredienteService service;

  public IngredienteController(IngredienteService service) {
    this.service = service;
  }

  @GetMapping
  public List<IngredienteReadDTO> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<IngredienteReadDTO> findOne(@PathVariable Long id) {
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<IngredienteReadDTO> create(@Valid @RequestBody IngredienteWriteDTO body) {
    var saved = service.create(body);
    return ResponseEntity.created(URI.create("/api/ingredientes/" + saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<IngredienteReadDTO> update(@PathVariable Long id, @Valid @RequestBody IngredienteWriteDTO body) {
    return service.update(id, body)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!service.delete(id)) return ResponseEntity.notFound().build();
    return ResponseEntity.noContent().build();
  }
}
