package com.pitty.controller;

import com.pitty.domain.Pedido;
import com.pitty.dto.PedidoCreateDTO;
import com.pitty.service.PedidoService;
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
  public List<Pedido> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> findOne(@PathVariable Long id) {
    return service
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Pedido> create(@RequestBody PedidoCreateDTO dto) {
    try {
      var pedido = service.create(dto);
      return ResponseEntity.created(URI.create("/api/pedidos/" + pedido.getId()))
          .body(pedido);
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.badRequest().build();
    }
  }
}
