package com.pitty.controller;

import com.pitty.domain.Ingrediente;
import com.pitty.repository.IngredienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

  private final IngredienteRepository repo;

  public IngredienteController(IngredienteRepository repo) {
    this.repo = repo;
  }

  // GET /api/ingredientes
  @GetMapping
  public List<Ingrediente> findAll() {
    return repo.findAll();
  }

  // GET /api/ingredientes/{id}
  @GetMapping("/{id}")
  public ResponseEntity<Ingrediente> findOne(@PathVariable Long id) {
    return repo.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // POST /api/ingredientes
  @PostMapping
  public ResponseEntity<Ingrediente> create(@Valid @RequestBody Ingrediente body) {
    // createdAt/updatedAt se llenan por @PrePersist en Auditable
    var saved = repo.save(body);
    return ResponseEntity.created(URI.create("/api/ingredientes/" + saved.getId())).body(saved);
  }

  // PUT /api/ingredientes/{id}
  @PutMapping("/{id}")
  public ResponseEntity<Ingrediente> update(@PathVariable Long id, @Valid @RequestBody Ingrediente body) {
    return repo.findById(id)
        .map(existing -> {
          existing.setNombre(body.getNombre());
          existing.setUnidad(body.getUnidad());
          existing.setStockActual(body.getStockActual());
          existing.setStockMinimo(body.getStockMinimo());
          existing.setActivo(body.getActivo());
          existing.setUpdatedBy(body.getUpdatedBy());
          var saved = repo.save(existing);
          return ResponseEntity.ok(saved);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // DELETE /api/ingredientes/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
