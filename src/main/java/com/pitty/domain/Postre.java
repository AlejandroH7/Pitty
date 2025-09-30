package com.pitty.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "postre")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Postre extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 120)
  private String nombre;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal precio;

  @Column(nullable = false)
  private Integer porciones;

  @Builder.Default
  @Column(nullable = false)
  private Boolean activo = true;

  // Solo lectura desde el lado Postre por ahora
  @OneToMany(mappedBy = "postre", orphanRemoval = true)
  private List<RecetaDetalle> receta;
}
