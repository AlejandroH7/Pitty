package com.pitty.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ingrediente") // en schema public
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ingrediente extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 120)
  private String nombre;

  @Column(nullable = false, length = 20)
  private String unidad; // g, ml, unidad, etc.

  @Column(name = "stock_actual", nullable = false, precision = 12, scale = 3)
  private BigDecimal stockActual;

  @Column(name = "stock_minimo", nullable = false, precision = 12, scale = 3)
  private BigDecimal stockMinimo;

  @Builder.Default
  @Column(nullable = false)
  private Boolean activo = true;
}

