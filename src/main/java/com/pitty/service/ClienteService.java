package com.pitty.service;

import com.pitty.domain.Cliente;
import com.pitty.dto.ClienteReadDTO;
import com.pitty.dto.ClienteWriteDTO;
import com.pitty.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClienteReadDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ClienteReadDTO> findById(Long id) {
        return repository.findById(id).map(this::toReadDto);
    }

    public ClienteReadDTO create(ClienteWriteDTO dto) {
        Cliente entity = new Cliente();
        applyChanges(entity, dto);
        return toReadDto(repository.save(entity));
    }

    public Optional<ClienteReadDTO> update(Long id, ClienteWriteDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    applyChanges(entity, dto);
                    return toReadDto(repository.save(entity));
                });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    private void applyChanges(Cliente entity, ClienteWriteDTO dto) {
        entity.setNombre(dto.getNombre());
        entity.setTelefono(dto.getTelefono());
        entity.setNotas(dto.getNotas());
        if (dto.getCreatedBy() != null) {
            entity.setCreatedBy(dto.getCreatedBy());
        }
        entity.setUpdatedBy(dto.getUpdatedBy());
    }

    private ClienteReadDTO toReadDto(Cliente entity) {
        return ClienteReadDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .telefono(entity.getTelefono())
                .notas(entity.getNotas())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
