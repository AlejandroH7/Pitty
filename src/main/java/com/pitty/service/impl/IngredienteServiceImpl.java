package com.pitty.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitty.domain.Ingrediente;
import com.pitty.dto.ingrediente.IngredienteRequestDTO;
import com.pitty.dto.ingrediente.IngredienteResponseDTO;
import com.pitty.exception.ConflictException;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.IngredienteMapper;
import com.pitty.repository.IngredienteRepository;
import com.pitty.service.IngredienteService;

@Service
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteRepository repo;

    public IngredienteServiceImpl(IngredienteRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredienteResponseDTO> listar() {
        return repo.findAll().stream()
            .map(IngredienteMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredienteResponseDTO obtener(Long id) {
        var ing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado"));
        return IngredienteMapper.toResponse(ing);
    }

    @Override
    @Transactional
    public IngredienteResponseDTO crear(IngredienteRequestDTO dto) {
        if (repo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe un ingrediente con ese nombre");
        }
        Ingrediente ing = IngredienteMapper.toEntity(dto);
        ing = repo.save(ing);
        return IngredienteMapper.toResponse(ing);
    }

    @Override
    @Transactional
    public IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto) {
        var ing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado"));
        if (repo.existsByNombreIgnoreCaseAndIdNot(dto.getNombre(), id)) {
            throw new ConflictException("Ya existe otro ingrediente con ese nombre");
        }
        IngredienteMapper.copyToEntity(dto, ing);
        ing = repo.save(ing);
        return IngredienteMapper.toResponse(ing);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Ingrediente no encontrado");
        }
        // Si hay FKs (p.ej. receta_detalle), la DB lanzará DataIntegrityViolationException
        // y tu GlobalExceptionHandler la convertirá en 409 automáticamente.
        repo.deleteById(id);
    }
}