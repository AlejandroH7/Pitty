package com.pitty.controller;

import com.pitty.domain.Postre;
import com.pitty.repository.PostreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/postres")
public class PostreController {

  private final PostreRepository repo;

  public PostreController(PostreRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Postre> all() { return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Postre> one(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Postre> create(@RequestBody Postre body) {
    var saved = repo.save(body);
    return ResponseEntity.created(URI.create("/api/postres/" + saved.getId())).body(saved);
  }
}
