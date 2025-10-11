// src/main/java/com/pitty/service/impl/PostreServiceImpl.java
package com.pitty.service.impl;

import com.pitty.domain.Postre;
import com.pitty.dto.postre.PostreRequestDTO;
import com.pitty.dto.postre.PostreResponseDTO;
import com.pitty.exception.ConflictException;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.PostreMapper;
import com.pitty.repository.PostreRepository;
import com.pitty.service.PostreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostreServiceImpl implements PostreService {

    private final PostreRepository repository;

    public PostreServiceImpl(PostreRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public PostreResponseDTO create(PostreRequestDTO dto) {
        // regla: nombre único
        if (repository.existsByNombreIgnoreCase(dto.nombre())) {
            throw new ConflictException("Ya existe un postre con el nombre: " + dto.nombre());
        }
        Postre entity = PostreMapper.toEntity(dto);
        entity = repository.save(entity);
        return PostreMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PostreResponseDTO getById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Postre " + id + " no encontrado"));
        return PostreMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostreResponseDTO> list() {
        return repository.findAll()
                .stream()
                .map(PostreMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public PostreResponseDTO update(Long id, PostreRequestDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Postre " + id + " no encontrado"));

        // regla: nombre único (excluyendo el mismo id)
        if (repository.existsByNombreIgnoreCaseAndIdNot(dto.nombre(), id)) {
            throw new ConflictException("Ya existe un postre con el nombre: " + dto.nombre());
        }

        PostreMapper.copy(dto, entity);
        entity = repository.save(entity);
        return PostreMapper.toResponse(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Postre " + id + " no encontrado"));
        repository.delete(entity);
        // Si hay FK (pedido_item) tu handler devolverá 409 automáticamente.
    }
}