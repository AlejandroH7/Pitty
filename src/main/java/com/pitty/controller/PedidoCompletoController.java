package com.pitty.controller;

import com.pitty.dto.pedido_completo.PedidoCompletoCreateDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoResponseDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoUpdateDTO;
import com.pitty.service.PedidoCompletoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/pedido-completos")
@Tag(name = "Pedido Completo", description = "Operaciones para gestionar pedidos completos")
public class PedidoCompletoController {

    private final PedidoCompletoService pedidoCompletoService;

    public PedidoCompletoController(PedidoCompletoService pedidoCompletoService) {
        this.pedidoCompletoService = pedidoCompletoService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido completo", description = "Registra un nuevo pedido en el sistema.")
    public ResponseEntity<PedidoCompletoResponseDTO> create(@Valid @RequestBody PedidoCompletoCreateDTO createDTO) {
        PedidoCompletoResponseDTO responseDTO = pedidoCompletoService.create(createDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pedido por ID", description = "Recupera los detalles de un pedido específico.")
    public ResponseEntity<PedidoCompletoResponseDTO> getById(
            @Parameter(description = "ID del pedido a obtener", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(pedidoCompletoService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos con filtros", description = "Lista pedidos de forma paginada con filtros opcionales.")
    public ResponseEntity<Page<PedidoCompletoResponseDTO>> list(
            @Parameter(description = "ID del cliente para filtrar", example = "1")
            @RequestParam(required = false) Long clienteId,
            @Parameter(description = "ID del postre para filtrar", example = "1")
            @RequestParam(required = false) Long postreId,
            @Parameter(description = "Fecha de inicio del rango de entrega (formato ISO OffsetDateTime)", example = "2025-10-01T00:00:00-06:00")
            @RequestParam(required = false) OffsetDateTime from,
            @Parameter(description = "Fecha de fin del rango de entrega (formato ISO OffsetDateTime)", example = "2025-10-31T23:59:59-06:00")
            @RequestParam(required = false) OffsetDateTime to,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(pedidoCompletoService.list(clienteId, postreId, from, to, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido completo", description = "Actualiza todos los datos de un pedido existente usando PUT.")
    public ResponseEntity<PedidoCompletoResponseDTO> update(
            @Parameter(description = "ID del pedido a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PedidoCompletoUpdateDTO updateDTO) {
        return ResponseEntity.ok(pedidoCompletoService.update(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido del sistema por su ID.")
    public void delete(
            @Parameter(description = "ID del pedido a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        pedidoCompletoService.delete(id);
    }
}
