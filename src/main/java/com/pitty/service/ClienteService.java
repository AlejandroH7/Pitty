package com.pitty.service;

import com.pitty.domain.Cliente;
import com.pitty.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

  private final ClienteRepository repository;

  public ClienteService(ClienteRepository repository) {
    this.repository = repository;
  }

  public List<Cliente> findAll() {
    return repository.findAll();
  }

  public Optional<Cliente> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Cliente create(Cliente cliente) {
    return repository.save(cliente);
  }

  @Transactional
  public Optional<Cliente> update(Long id, Cliente body) {
    return repository
        .findById(id)
        .map(
            existing -> {
              existing.setNombre(body.getNombre());
              existing.setTelefono(body.getTelefono());
              existing.setNotas(body.getNotas());
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

