package com.pitty.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
  name = "receta_detalle",
  uniqueConstraints = @UniqueConstraint(columnNames = {"postre_id","ingrediente_id"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecetaDetalle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "postre_id")
  private Postre postre;

  @ManyToOne(optional = false) @JoinColumn(name = "ingrediente_id")
  private Ingrediente ingrediente;

  @Column(name = "cantidad_por_postre", nullable = false, precision = 12, scale = 3)
  private BigDecimal cantidadPorPostre;

  @Column(name = "merma_pct", nullable = false, precision = 5, scale = 2)
  private BigDecimal mermaPct; // 0..100
}
