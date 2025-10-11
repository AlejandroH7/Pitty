package com.pitty.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pitty.dto.PedidoCreateDTO;
import com.pitty.dto.PedidoEstadoUpdateDTO;
import com.pitty.dto.PedidoResponseDTO;
import com.pitty.service.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // POST /api/pedidos
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@Valid @RequestBody PedidoCreateDTO dto) {
        PedidoResponseDTO resp = pedidoService.crear(dto);
        return ResponseEntity
                .created(URI.create("/api/pedidos/" + resp.getId()))
                .body(resp);
    }

    // GET /api/pedidos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    // GET /api/pedidos?q=&desde=&hasta=
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(pedidoService.listar(q, desde, hasta));
    }

    // PUT /api/pedidos/{id}/estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> updateEstado(
            @PathVariable Long id,
            @Valid @RequestBody PedidoEstadoUpdateDTO dto) {
        return ResponseEntity.ok(pedidoService.updateEstado(id, dto));
    }

    // DELETE /api/pedidos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
