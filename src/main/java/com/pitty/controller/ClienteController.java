package com.pitty.controller;

import com.pitty.dto.ClienteReadDTO;
import com.pitty.dto.ClienteWriteDTO;
import com.pitty.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  private final ClienteService service;

  public ClienteController(ClienteService service) {
    this.service = service;
  }

  @GetMapping
  public List<ClienteReadDTO> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteReadDTO> findOne(@PathVariable Long id) {
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ClienteReadDTO> create(@Valid @RequestBody ClienteWriteDTO body) {
    var saved = service.create(body);
    return ResponseEntity.created(URI.create("/api/clientes/" + saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClienteReadDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteWriteDTO body) {
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
