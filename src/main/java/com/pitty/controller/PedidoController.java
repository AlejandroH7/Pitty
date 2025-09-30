package com.pitty.controller;

import com.pitty.dto.PedidoReadDTO;
import com.pitty.dto.PedidoWriteDTO;
import com.pitty.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

  private final PedidoService service;

  public PedidoController(PedidoService service) {
    this.service = service;
  }

  @GetMapping
  public List<PedidoReadDTO> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoReadDTO> findOne(@PathVariable Long id) {
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<PedidoReadDTO> create(@Valid @RequestBody PedidoWriteDTO dto) {
    return service.create(dto)
        .map(saved -> ResponseEntity.created(URI.create("/api/pedidos/" + saved.getId()))
            .body(saved))
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }
}
