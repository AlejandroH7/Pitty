// src/main/java/com/pitty/repository/PostreRepository.java
package com.pitty.repository;

import com.pitty.domain.Postre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostreRepository extends JpaRepository<Postre, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}