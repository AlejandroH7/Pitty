package com.pitty.mapper;

import com.pitty.domain.Cliente;
import com.pitty.dto.ClienteRequestDTO;
import com.pitty.dto.ClienteResponseDTO;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.getNombre());
        c.setTelefono(dto.getTelefono());
        c.setNotas(dto.getNotas());
        return c;
    }

    public static void updateEntity(Cliente c, ClienteRequestDTO dto) {
        c.setNombre(dto.getNombre());
        c.setTelefono(dto.getTelefono());
        c.setNotas(dto.getNotas());
    }

    public static ClienteResponseDTO toResponse(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setTelefono(c.getTelefono());
        dto.setNotas(c.getNotas());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setCreatedBy(c.getCreatedBy());
        dto.setUpdatedAt(c.getUpdatedAt());
        dto.setUpdatedBy(c.getUpdatedBy());
        return dto;
    }
}
