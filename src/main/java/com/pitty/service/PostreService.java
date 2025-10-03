// src/main/java/com/pitty/service/PostreService.java
package com.pitty.service;

import com.pitty.dto.postre.PostreRequestDTO;
import com.pitty.dto.postre.PostreResponseDTO;

import java.util.List;

public interface PostreService {
    PostreResponseDTO create(PostreRequestDTO dto);
    PostreResponseDTO getById(Long id);
    List<PostreResponseDTO> list();
    PostreResponseDTO update(Long id, PostreRequestDTO dto);
    void delete(Long id);
}