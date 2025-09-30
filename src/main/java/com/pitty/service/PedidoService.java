package com.pitty.service;

import com.pitty.domain.Cliente;
import com.pitty.domain.Pedido;
import com.pitty.domain.PedidoItem;
import com.pitty.domain.Postre;
import com.pitty.dto.PedidoCreateDTO;
import com.pitty.dto.PedidoItemCreateDTO;
import com.pitty.repository.ClienteRepository;
import com.pitty.repository.PedidoItemRepository;
import com.pitty.repository.PedidoRepository;
import com.pitty.repository.PostreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

  private final PedidoRepository pedidoRepository;
  private final PedidoItemRepository itemRepository;
  private final ClienteRepository clienteRepository;
  private final PostreRepository postreRepository;

  public PedidoService(
      PedidoRepository pedidoRepository,
      PedidoItemRepository itemRepository,
      ClienteRepository clienteRepository,
      PostreRepository postreRepository) {
    this.pedidoRepository = pedidoRepository;
    this.itemRepository = itemRepository;
    this.clienteRepository = clienteRepository;
    this.postreRepository = postreRepository;
  }

  public List<Pedido> findAll() {
    return pedidoRepository.findAll();
  }

  public Optional<Pedido> findById(Long id) {
    return pedidoRepository.findById(id);
  }

  @Transactional
  public Pedido create(PedidoCreateDTO dto) {
    Cliente cliente =
        Optional.ofNullable(dto.getClienteId())
            .flatMap(clienteRepository::findById)
            .orElseThrow(() -> new IllegalArgumentException("Cliente inexistente"));

    if (dto.getItems() == null || dto.getItems().isEmpty()) {
      throw new IllegalArgumentException("El pedido debe incluir al menos un item");
    }

    Pedido pedido = new Pedido();
    pedido.setCliente(cliente);
    pedido.setFechaEntrega(dto.getFechaEntrega());
    pedido.setEstado(Pedido.Estado.BORRADOR);
    pedido.setNotas(dto.getNotas());
    pedido.setTotal(BigDecimal.ZERO);
    pedido = pedidoRepository.save(pedido);

    BigDecimal total = BigDecimal.ZERO;
    for (PedidoItemCreateDTO itemDTO : dto.getItems()) {
      Postre postre =
          Optional.ofNullable(itemDTO.getPostreId())
              .flatMap(postreRepository::findById)
              .orElseThrow(() -> new IllegalArgumentException("Postre inexistente"));

      if (itemDTO.getCantidad() == null || itemDTO.getCantidad() <= 0) {
        throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
      }
      if (itemDTO.getPrecioUnitario() == null || itemDTO.getPrecioUnitario().signum() < 0) {
        throw new IllegalArgumentException("El precio unitario debe ser positivo");
      }

      PedidoItem item = new PedidoItem();
      item.setPedido(pedido);
      item.setPostre(postre);
      item.setCantidad(itemDTO.getCantidad());
      item.setPrecioUnitario(itemDTO.getPrecioUnitario());
      BigDecimal subtotal = itemDTO.getPrecioUnitario().multiply(new BigDecimal(itemDTO.getCantidad()));
      item.setSubtotal(subtotal);
      item.setPersonalizaciones(itemDTO.getPersonalizaciones());
      itemRepository.save(item);

      total = total.add(subtotal);
    }

    pedido.setTotal(total);
    return pedidoRepository.save(pedido);
  }
}

