package com.pitty.mapper;

import com.pitty.domain.Cliente;
import com.pitty.domain.PedidoCompleto;
import com.pitty.domain.Postre;
import com.pitty.dto.pedido_completo.PedidoCompletoCreateDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoResponseDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoCompletoMapper {

    public PedidoCompleto toEntity(PedidoCompletoCreateDTO dto, Cliente cliente, Postre postre) {
        PedidoCompleto pedido = new PedidoCompleto();
        pedido.setCliente(cliente);
        pedido.setPostre(postre);
        pedido.setNota(dto.getNota());
        pedido.setCantidad(dto.getCantidad());
        pedido.setTotal(dto.getTotal());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        return pedido;
    }

    public void applyUpdate(PedidoCompleto entity, PedidoCompletoUpdateDTO dto, Cliente cliente, Postre postre) {
        entity.setCliente(cliente);
        entity.setPostre(postre);
        entity.setNota(dto.getNota());
        entity.setCantidad(dto.getCantidad());
        entity.setTotal(dto.getTotal());
        entity.setFechaEntrega(dto.getFechaEntrega());
    }

    public PedidoCompletoResponseDTO toResponse(PedidoCompleto entity) {
        PedidoCompletoResponseDTO dto = new PedidoCompletoResponseDTO();
        dto.setId(entity.getId());
        dto.setNota(entity.getNota());
        dto.setCantidad(entity.getCantidad());
        dto.setTotal(entity.getTotal());
        dto.setFechaEntrega(entity.getFechaEntrega());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getCliente() != null) {
            dto.setClienteId(entity.getCliente().getId());
            dto.setClienteNombre(entity.getCliente().getNombre()); // Asumiendo que Cliente tiene un método getNombre()
        }

        if (entity.getPostre() != null) {
            dto.setPostreId(entity.getPostre().getId());
            dto.setPostreNombre(entity.getPostre().getNombre()); // Asumiendo que Postre tiene un método getNombre()
        }

        return dto;
    }
}
