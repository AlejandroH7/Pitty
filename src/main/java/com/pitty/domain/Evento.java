package com.pitty.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "evento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Evento extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 150)
  private String titulo;

  private String lugar;

  @Column(name = "fecha_hora", nullable = false)
  private OffsetDateTime fechaHora;

  @ManyToOne @JoinColumn(name = "pedido_id")
  private Pedido pedido; // opcional

  @Column(length = 500)
  private String notas;
}
