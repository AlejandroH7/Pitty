package com.pitty.controller;

import com.pitty.domain.Postre;
import com.pitty.service.PostreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/postres")
public class PostreController {

  private final PostreService service;

  public PostreController(PostreService service) { this.service = service; }

  @GetMapping
  public List<Postre> all() { return service.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Postre> one(@PathVariable Long id) {
    return service.findById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Postre> create(@RequestBody Postre body) {
    var saved = service.create(body);
    return ResponseEntity.created(URI.create("/api/postres/" + saved.getId())).body(saved);
  }
}
