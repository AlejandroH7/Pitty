package com.pitty.service;

import com.pitty.domain.Postre;
import com.pitty.repository.PostreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostreService {

  private final PostreRepository repository;

  public PostreService(PostreRepository repository) {
    this.repository = repository;
  }

  public List<Postre> findAll() {
    return repository.findAll();
  }

  public Optional<Postre> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Postre create(Postre body) {
    return repository.save(body);
  }
}

