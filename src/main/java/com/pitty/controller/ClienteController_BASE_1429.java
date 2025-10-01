package com.pitty.controller;

import com.pitty.domain.Cliente;
import com.pitty.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  private final ClienteRepository repo;

  public ClienteController(ClienteRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Cliente> findAll() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> findOne(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente body) {
    var saved = repo.save(body);
    return ResponseEntity.created(URI.create("/api/clientes/" + saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente body) {
    return repo.findById(id).map(existing -> {
      existing.setNombre(body.getNombre());
      existing.setTelefono(body.getTelefono());
      existing.setNotas(body.getNotas());
      existing.setUpdatedBy(body.getUpdatedBy());
      return ResponseEntity.ok(repo.save(existing));
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

