// src/main/java/com/pitty/repository/RecetaDetalleRepository.java
package com.pitty.repository;

import com.pitty.domain.RecetaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecetaDetalleRepository extends JpaRepository<RecetaDetalle, Long> {

    List<RecetaDetalle> findByPostreId(Long postreId);

    Optional<RecetaDetalle> findByPostreIdAndIngredienteId(Long postreId, Long ingredienteId);
}