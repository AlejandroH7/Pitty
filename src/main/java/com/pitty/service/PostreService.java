package com.pitty.service;

import com.pitty.domain.Postre;
import com.pitty.dto.PostreReadDTO;
import com.pitty.dto.PostreWriteDTO;
import com.pitty.repository.PostreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostreService {

    private final PostreRepository repository;

    public PostreService(PostreRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PostreReadDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PostreReadDTO> findById(Long id) {
        return repository.findById(id).map(this::toReadDto);
    }

    public PostreReadDTO create(PostreWriteDTO dto) {
        Postre entity = new Postre();
        applyChanges(entity, dto);
        return toReadDto(repository.save(entity));
    }

    private void applyChanges(Postre entity, PostreWriteDTO dto) {
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setPorciones(dto.getPorciones());
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : Boolean.TRUE);
        if (dto.getCreatedBy() != null) {
            entity.setCreatedBy(dto.getCreatedBy());
        }
        entity.setUpdatedBy(dto.getUpdatedBy());
    }

    private PostreReadDTO toReadDto(Postre entity) {
        return PostreReadDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .precio(entity.getPrecio())
                .porciones(entity.getPorciones())
                .activo(entity.getActivo())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
