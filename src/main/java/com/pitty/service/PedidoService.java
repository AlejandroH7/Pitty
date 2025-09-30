package com.pitty.service;

import com.pitty.domain.Cliente;
import com.pitty.domain.Pedido;
import com.pitty.domain.PedidoItem;
import com.pitty.domain.Postre;
import com.pitty.dto.PedidoItemReadDTO;
import com.pitty.dto.PedidoItemWriteDTO;
import com.pitty.dto.PedidoReadDTO;
import com.pitty.dto.PedidoWriteDTO;
import com.pitty.repository.ClienteRepository;
import com.pitty.repository.PedidoItemRepository;
import com.pitty.repository.PedidoRepository;
import com.pitty.repository.PostreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ClienteRepository clienteRepository;
    private final PostreRepository postreRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         PedidoItemRepository pedidoItemRepository,
                         ClienteRepository clienteRepository,
                         PostreRepository postreRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.clienteRepository = clienteRepository;
        this.postreRepository = postreRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoReadDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PedidoReadDTO> findById(Long id) {
        return pedidoRepository.findById(id).map(this::toReadDto);
    }

    public Optional<PedidoReadDTO> create(PedidoWriteDTO dto) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(dto.getClienteId());
        if (clienteOpt.isEmpty()) {
            return Optional.empty();
        }

        List<Postre> postres = new ArrayList<>();
        if (dto.getItems() != null) {
            for (PedidoItemWriteDTO itemDto : dto.getItems()) {
                Optional<Postre> postreOpt = postreRepository.findById(itemDto.getPostreId());
                if (postreOpt.isEmpty()) {
                    return Optional.empty();
                }
                postres.add(postreOpt.get());
            }
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteOpt.get());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setEstado(dto.getEstado() != null ? dto.getEstado() : Pedido.Estado.BORRADOR);
        pedido.setNotas(dto.getNotas());
        pedido.setTotal(BigDecimal.ZERO);
        if (dto.getCreatedBy() != null) {
            pedido.setCreatedBy(dto.getCreatedBy());
        }
        pedido.setUpdatedBy(dto.getUpdatedBy());
        pedido = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        List<PedidoItem> persistedItems = new ArrayList<>();
        if (dto.getItems() != null) {
            for (int i = 0; i < dto.getItems().size(); i++) {
                PedidoItemWriteDTO itemDto = dto.getItems().get(i);
                Postre postre = postres.get(i);

                PedidoItem item = new PedidoItem();
                item.setPedido(pedido);
                item.setPostre(postre);
                item.setCantidad(itemDto.getCantidad());
                item.setPrecioUnitario(itemDto.getPrecioUnitario());
                BigDecimal subtotal = itemDto.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(itemDto.getCantidad()));
                item.setSubtotal(subtotal);
                item.setPersonalizaciones(itemDto.getPersonalizaciones());

                item = pedidoItemRepository.save(item);
                persistedItems.add(item);
                total = total.add(subtotal);
            }
        }

        pedido.setItems(persistedItems);
        pedido.setTotal(total);
        pedido = pedidoRepository.save(pedido);

        return pedidoRepository.findById(pedido.getId()).map(this::toReadDto);
    }

    private PedidoReadDTO toReadDto(Pedido entity) {
        return PedidoReadDTO.builder()
                .id(entity.getId())
                .clienteId(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .clienteNombre(entity.getCliente() != null ? entity.getCliente().getNombre() : null)
                .fechaEntrega(entity.getFechaEntrega())
                .estado(entity.getEstado())
                .notas(entity.getNotas())
                .total(entity.getTotal())
                .items(mapItems(entity.getItems()))
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private List<PedidoItemReadDTO> mapItems(List<PedidoItem> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(item -> PedidoItemReadDTO.builder()
                        .id(item.getId())
                        .postreId(item.getPostre() != null ? item.getPostre().getId() : null)
                        .postreNombre(item.getPostre() != null ? item.getPostre().getNombre() : null)
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .subtotal(item.getSubtotal())
                        .personalizaciones(item.getPersonalizaciones())
                        .build())
                .collect(Collectors.toList());
    }
}
