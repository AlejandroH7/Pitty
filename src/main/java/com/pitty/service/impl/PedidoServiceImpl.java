package com.pitty.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pitty.domain.Cliente;
import com.pitty.domain.Pedido;
import com.pitty.domain.PedidoItem;
import com.pitty.domain.Postre;

import com.pitty.dto.PedidoCreateDTO;
import com.pitty.dto.PedidoEstadoUpdateDTO;
import com.pitty.dto.PedidoItemCreateDTO;
import com.pitty.dto.PedidoItemResponseDTO;
import com.pitty.dto.PedidoResponseDTO;

import com.pitty.exception.NotFoundException;

import com.pitty.repository.ClienteRepository;
import com.pitty.repository.PedidoItemRepository;
import com.pitty.repository.PedidoRepository;
import com.pitty.repository.PostreRepository;

import com.pitty.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepo;
    private final PedidoItemRepository itemRepo;
    private final PostreRepository postreRepo;
    private final ClienteRepository clienteRepo;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public PedidoResponseDTO crear(PedidoCreateDTO dto) {

        Cliente cliente = null;
        if (dto.getClienteId() != null) {
            cliente = clienteRepo.findById(dto.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente id=" + dto.getClienteId() + " no existe"));
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setEstado(Pedido.Estado.BORRADOR);
        pedido.setNotas(dto.getNotas());
        pedido.setTotal(BigDecimal.ZERO);
        pedido = pedidoRepo.save(pedido);

        BigDecimal total = BigDecimal.ZERO;

        if (dto.getItems() != null) {
            for (PedidoItemCreateDTO it : dto.getItems()) {

                Postre postre = null;
                if (it.getPostreId() != null) {
                    postre = postreRepo.findById(it.getPostreId())
                            .orElseThrow(() -> new NotFoundException("Postre id=" + it.getPostreId() + " no existe"));
                }

                PedidoItem item = new PedidoItem();
                item.setPedido(pedido);
                item.setPostre(postre);
                item.setCantidad(it.getCantidad());
                item.setPrecioUnitario(it.getPrecioUnitario());

                BigDecimal subtotal = it.getPrecioUnitario()
                        .multiply(new BigDecimal(it.getCantidad() == null ? 0 : it.getCantidad()));
                item.setSubtotal(subtotal);

                // Guarda el Map<String,Object> tal cual en la entidad (columna jsonb)
                item.setPersonalizaciones(objectMapper.valueToTree(it.getPersonalizaciones()));


                itemRepo.save(item);
                total = total.add(subtotal);
            }
        }

        pedido.setTotal(total);
        pedido = pedidoRepo.save(pedido);

        return toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido id=" + id + " no existe"));
        return toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listar(String q, LocalDate desde, LocalDate hasta) {
        // Por ahora simple: todos. Luego podemos aplicar filtros por q/fechas.
        return pedidoRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public PedidoResponseDTO updateEstado(Long id, PedidoEstadoUpdateDTO dto) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido id=" + id + " no existe"));
        if (dto.getEstado() != null) {
            try {
                pedido.setEstado(Pedido.Estado.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException ex) {
                throw new NotFoundException("Estado inválido: " + dto.getEstado());
            }
        }
        pedido = pedidoRepo.save(pedido);
        return toResponse(pedido);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!pedidoRepo.existsById(id)) {
            throw new NotFoundException("Pedido id=" + id + " no existe");
        }
        pedidoRepo.deleteById(id);
    }

    // ---------- MAPEOS ----------

    private PedidoResponseDTO toResponse(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setClienteNombre(pedido.getCliente().getNombre());
        }
        dto.setFechaEntrega(pedido.getFechaEntrega());
        dto.setEstado(pedido.getEstado() != null ? pedido.getEstado().name() : null);
        dto.setNotas(pedido.getNotas());
        dto.setTotal(pedido.getTotal());

        if (pedido.getItems() != null) {
            dto.setItems(pedido.getItems().stream().map(this::toItem).toList());
        }
        return dto;
    }

    private PedidoItemResponseDTO toItem(PedidoItem it) {
        PedidoItemResponseDTO dto = new PedidoItemResponseDTO();

        if (it.getPostre() != null) {
            dto.setPostreId(it.getPostre().getId());
            dto.setPostreNombre(it.getPostre().getNombre());
        }
        dto.setCantidad(it.getCantidad());
        dto.setPrecioUnitario(it.getPrecioUnitario());
        dto.setSubtotal(it.getSubtotal());

        // Convertimos el Map<String,Object> a JsonNode para el DTO de salida
        if (it.getPersonalizaciones() != null) {
            JsonNode node = objectMapper.valueToTree(it.getPersonalizaciones());
            dto.setPersonalizaciones(node);
        }
        return dto;
    }
}
