// src/main/java/com/pitty/controller/PostreController.java
package com.pitty.controller;

import com.pitty.dto.postre.PostreRequestDTO;
import com.pitty.dto.postre.PostreResponseDTO;
import com.pitty.service.PostreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/postres")
public class PostreController {

    private final PostreService service;

    public PostreController(PostreService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostreResponseDTO> create(@Valid @RequestBody PostreRequestDTO dto) {
        var created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/postres/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostreResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PostreResponseDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostreResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody PostreRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}