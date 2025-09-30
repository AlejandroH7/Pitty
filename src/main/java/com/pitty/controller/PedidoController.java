package com.pitty.controller;

import com.pitty.domain.*;
import com.pitty.dto.PedidoCreateDTO;
import com.pitty.dto.PedidoItemCreateDTO;
import com.pitty.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

  private final PedidoRepository pedidoRepo;
  private final PedidoItemRepository itemRepo;
  private final ClienteRepository clienteRepo;
  private final PostreRepository postreRepo;

  public PedidoController(PedidoRepository pedidoRepo,
                          PedidoItemRepository itemRepo,
                          ClienteRepository clienteRepo,
                          PostreRepository postreRepo) {
    this.pedidoRepo = pedidoRepo;
    this.itemRepo = itemRepo;
    this.clienteRepo = clienteRepo;
    this.postreRepo = postreRepo;
  }

  @GetMapping
  public List<Pedido> findAll() {
    return pedidoRepo.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> findOne(@PathVariable Long id) {
    return pedidoRepo.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Pedido> create(@RequestBody PedidoCreateDTO dto) {
    var cliente = (dto.getClienteId() != null)
        ? clienteRepo.findById(dto.getClienteId()).orElse(null) : null;
    if (cliente == null) {
      return ResponseEntity.badRequest().build();
    }

    var pedido = new Pedido();
    pedido.setCliente(cliente);
    pedido.setFechaEntrega(dto.getFechaEntrega());
    pedido.setEstado(Pedido.Estado.BORRADOR);
    pedido.setNotas(dto.getNotas());
    pedido.setTotal(BigDecimal.ZERO);
    pedido = pedidoRepo.save(pedido);

    BigDecimal total = BigDecimal.ZERO;
    if (dto.getItems() != null) {
      for (PedidoItemCreateDTO it : dto.getItems()) {
        var postre = (it.getPostreId() != null)
            ? postreRepo.findById(it.getPostreId()).orElse(null) : null;
        if (postre == null) continue;

        var item = new PedidoItem();
        item.setPedido(pedido);
        item.setPostre(postre);
        item.setCantidad(it.getCantidad());
        item.setPrecioUnitario(it.getPrecioUnitario());
        var subtotal = it.getPrecioUnitario()
            .multiply(new BigDecimal(it.getCantidad()));
        item.setSubtotal(subtotal);
        item.setPersonalizaciones(it.getPersonalizaciones());
        itemRepo.save(item);

        total = total.add(subtotal);
      }
    }

    pedido.setTotal(total);
    pedido = pedidoRepo.save(pedido);

    return ResponseEntity.created(URI.create("/api/pedidos/" + pedido.getId()))
        .body(pedido);
  }
}
