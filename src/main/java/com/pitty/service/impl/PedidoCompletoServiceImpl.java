package com.pitty.service.impl;

import com.pitty.domain.Cliente;
import com.pitty.domain.PedidoCompleto;
import com.pitty.domain.Postre;
import com.pitty.dto.pedido_completo.PedidoCompletoCreateDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoResponseDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoUpdateDTO;
import com.pitty.exception.ConflictException;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.PedidoCompletoMapper;
import com.pitty.repository.ClienteRepository;
import com.pitty.repository.PedidoCompletoRepository;
import com.pitty.repository.PostreRepository;
import com.pitty.service.PedidoCompletoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class PedidoCompletoServiceImpl implements PedidoCompletoService {

    private final PedidoCompletoRepository pedidoCompletoRepository;
    private final ClienteRepository clienteRepository;
    private final PostreRepository postreRepository;
    private final PedidoCompletoMapper pedidoCompletoMapper;

    public PedidoCompletoServiceImpl(PedidoCompletoRepository pedidoCompletoRepository, ClienteRepository clienteRepository, PostreRepository postreRepository, PedidoCompletoMapper pedidoCompletoMapper) {
        this.pedidoCompletoRepository = pedidoCompletoRepository;
        this.clienteRepository = clienteRepository;
        this.postreRepository = postreRepository;
        this.pedidoCompletoMapper = pedidoCompletoMapper;
    }

    @Override
    @Transactional
    public PedidoCompletoResponseDTO create(PedidoCompletoCreateDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + dto.getClienteId()));
        Postre postre = postreRepository.findById(dto.getPostreId())
                .orElseThrow(() -> new NotFoundException("Postre no encontrado con ID: " + dto.getPostreId()));

        // DEBUG: Print values before stock validation
        System.out.println("DEBUG: Postre ID: " + postre.getId() + ", Nombre: " + postre.getNombre());
        System.out.println("DEBUG: Porciones disponibles: " + postre.getPorciones());
        System.out.println("DEBUG: Cantidad solicitada: " + dto.getCantidad());

        // 1. Validar stock
        if (postre.getPorciones() < dto.getCantidad()) {
            throw new ConflictException("No hay suficientes porciones disponibles para el postre: " + postre.getNombre() + ". Disponibles: " + postre.getPorciones());
        }

        // 2. Actualizar stock del postre
        postre.setPorciones(postre.getPorciones() - dto.getCantidad());

        PedidoCompleto pedido = pedidoCompletoMapper.toEntity(dto, cliente, postre);
        pedido.setCreatedAt(OffsetDateTime.now()); // Seteando fecha de creación

        PedidoCompleto savedPedido = pedidoCompletoRepository.save(pedido);
        return pedidoCompletoMapper.toResponse(savedPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoCompletoResponseDTO getById(Long id) {
        PedidoCompleto pedido = pedidoCompletoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID: " + id));
        return pedidoCompletoMapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoCompletoResponseDTO> list(Long clienteId, Long postreId, OffsetDateTime from, OffsetDateTime to, Pageable pageable) {
        Page<PedidoCompleto> page;
        if (clienteId != null) {
            page = pedidoCompletoRepository.findByClienteId(clienteId, pageable);
        } else if (postreId != null) {
            page = pedidoCompletoRepository.findByPostreId(postreId, pageable);
        } else if (from != null && to != null) {
            page = pedidoCompletoRepository.findByFechaEntregaBetween(from, to, pageable);
        } else {
            page = pedidoCompletoRepository.findAll(pageable);
        }
        return page.map(pedidoCompletoMapper::toResponse);
    }

    @Override
    @Transactional
    public PedidoCompletoResponseDTO update(Long id, PedidoCompletoUpdateDTO dto) {
        PedidoCompleto oldPedido = pedidoCompletoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID: " + id));

        Cliente newCliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + dto.getClienteId()));
        Postre newPostre = postreRepository.findById(dto.getPostreId())
                .orElseThrow(() -> new NotFoundException("Postre no encontrado con ID: " + dto.getPostreId()));

        Postre oldPostre = oldPedido.getPostre();
        int oldCantidad = oldPedido.getCantidad();
        int newCantidad = dto.getCantidad();

        // Lógica de ajuste de stock
        if (newPostre.getId().equals(oldPostre.getId())) {
            // El postre es el mismo, solo ajustamos la cantidad
            int quantityDifference = newCantidad - oldCantidad;

            if (quantityDifference > 0) {
                // Cantidad aumentada, validar stock
                if (newPostre.getPorciones() < quantityDifference) {
                    throw new ConflictException("No hay suficientes porciones disponibles para el postre: " + newPostre.getNombre() + ". Disponibles: " + newPostre.getPorciones());
                }
                newPostre.setPorciones(newPostre.getPorciones() - quantityDifference);
            } else if (quantityDifference < 0) {
                // Cantidad disminuida, devolver stock
                newPostre.setPorciones(newPostre.getPorciones() - quantityDifference); // Restar un negativo suma
            }
            // Si quantityDifference es 0, no hay cambio de stock
        } else {
            // El postre ha cambiado
            // 1. Devolver stock al postre antiguo
            oldPostre.setPorciones(oldPostre.getPorciones() + oldCantidad);

            // 2. Validar y descontar stock del nuevo postre
            if (newPostre.getPorciones() < newCantidad) {
                throw new ConflictException("No hay suficientes porciones disponibles para el postre: " + newPostre.getNombre() + ". Disponibles: " + newPostre.getPorciones());
            }
            newPostre.setPorciones(newPostre.getPorciones() - newCantidad);
        }

        // Guardar los cambios en los postres (si se modificaron)
        postreRepository.save(oldPostre); // Guarda el postre antiguo (si se modificó)
        if (!newPostre.getId().equals(oldPostre.getId())) {
            postreRepository.save(newPostre); // Guarda el nuevo postre (si es diferente y se modificó)
        }

        // Aplicar la actualización al pedido
        pedidoCompletoMapper.applyUpdate(oldPedido, dto, newCliente, newPostre);
        PedidoCompleto updatedPedido = pedidoCompletoRepository.save(oldPedido);
        return pedidoCompletoMapper.toResponse(updatedPedido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        PedidoCompleto pedido = pedidoCompletoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID: " + id));

        Postre postre = pedido.getPostre();
        postre.setPorciones(postre.getPorciones() + pedido.getCantidad());
        postreRepository.save(postre);

        pedidoCompletoRepository.delete(pedido);
    }
}
