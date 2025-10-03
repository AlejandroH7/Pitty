package com.pitty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pitty.domain.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
    Optional<Ingrediente> findByNombreIgnoreCase(String nombre);
}