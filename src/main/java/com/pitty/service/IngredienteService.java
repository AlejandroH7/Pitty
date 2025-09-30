package com.pitty.service;

import com.pitty.domain.Ingrediente;
import com.pitty.dto.IngredienteReadDTO;
import com.pitty.dto.IngredienteWriteDTO;
import com.pitty.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredienteService {

    private final IngredienteRepository repository;

    public IngredienteService(IngredienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<IngredienteReadDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<IngredienteReadDTO> findById(Long id) {
        return repository.findById(id).map(this::toReadDto);
    }

    public IngredienteReadDTO create(IngredienteWriteDTO dto) {
        Ingrediente entity = new Ingrediente();
        applyChanges(entity, dto);
        return toReadDto(repository.save(entity));
    }

    public Optional<IngredienteReadDTO> update(Long id, IngredienteWriteDTO dto) {
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

    private void applyChanges(Ingrediente entity, IngredienteWriteDTO dto) {
        entity.setNombre(dto.getNombre());
        entity.setUnidad(dto.getUnidad());
        entity.setStockActual(dto.getStockActual());
        entity.setStockMinimo(dto.getStockMinimo());
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : Boolean.TRUE);
        if (dto.getCreatedBy() != null) {
            entity.setCreatedBy(dto.getCreatedBy());
        }
        entity.setUpdatedBy(dto.getUpdatedBy());
    }

    private IngredienteReadDTO toReadDto(Ingrediente entity) {
        return IngredienteReadDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .unidad(entity.getUnidad())
                .stockActual(entity.getStockActual())
                .stockMinimo(entity.getStockMinimo())
                .activo(entity.getActivo())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
