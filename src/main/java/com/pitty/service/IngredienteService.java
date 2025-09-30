package com.pitty.service;

import com.pitty.domain.Ingrediente;
import com.pitty.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IngredienteService {

  private final IngredienteRepository repository;

  public IngredienteService(IngredienteRepository repository) {
    this.repository = repository;
  }

  public List<Ingrediente> findAll() {
    return repository.findAll();
  }

  public Optional<Ingrediente> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Ingrediente create(Ingrediente body) {
    return repository.save(body);
  }

  @Transactional
  public Optional<Ingrediente> update(Long id, Ingrediente body) {
    return repository
        .findById(id)
        .map(
            existing -> {
              existing.setNombre(body.getNombre());
              existing.setUnidad(body.getUnidad());
              existing.setStockActual(body.getStockActual());
              existing.setStockMinimo(body.getStockMinimo());
              existing.setActivo(body.getActivo());
              existing.setUpdatedBy(body.getUpdatedBy());
              return repository.save(existing);
            });
  }

  @Transactional
  public boolean delete(Long id) {
    if (!repository.existsById(id)) {
      return false;
    }
    repository.deleteById(id);
    return true;
  }
}

