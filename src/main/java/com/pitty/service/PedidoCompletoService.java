package com.pitty.service;

import com.pitty.dto.pedido_completo.PedidoCompletoCreateDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoResponseDTO;
import com.pitty.dto.pedido_completo.PedidoCompletoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public interface PedidoCompletoService {

    PedidoCompletoResponseDTO create(PedidoCompletoCreateDTO dto);

    PedidoCompletoResponseDTO getById(Long id);

    Page<PedidoCompletoResponseDTO> list(Long clienteId, Long postreId, OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    PedidoCompletoResponseDTO update(Long id, PedidoCompletoUpdateDTO dto);

    void delete(Long id);
}
