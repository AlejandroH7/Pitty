package com.pitty.controller;

import com.pitty.domain.Ingrediente;
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

  // GET /api/ingredientes
  @GetMapping
  public List<Ingrediente> findAll() {
    return service.findAll();
  }

  // GET /api/ingredientes/{id}
  @GetMapping("/{id}")
  public ResponseEntity<Ingrediente> findOne(@PathVariable Long id) {
    return service
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // POST /api/ingredientes
  @PostMapping
  public ResponseEntity<Ingrediente> create(@Valid @RequestBody Ingrediente body) {
    // createdAt/updatedAt se llenan por @PrePersist en Auditable
    var saved = service.create(body);
    return ResponseEntity.created(URI.create("/api/ingredientes/" + saved.getId())).body(saved);
  }

  // PUT /api/ingredientes/{id}
  @PutMapping("/{id}")
  public ResponseEntity<Ingrediente> update(@PathVariable Long id, @Valid @RequestBody Ingrediente body) {
    return service
        .update(id, body)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // DELETE /api/ingredientes/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!service.delete(id)) return ResponseEntity.notFound().build();
    return ResponseEntity.noContent().build();
  }
}
