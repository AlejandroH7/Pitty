package com.pitty.domain;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;  // ⬅️

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pedido extends Auditable {

  public enum Estado { BORRADOR, CONFIRMADO, CANCELADO, ENTREGADO }

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @Column(name = "fecha_entrega", nullable = false)
  private OffsetDateTime fechaEntrega;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 12)
  @Builder.Default
  private Estado estado = Estado.BORRADOR;

  @Column(length = 500)
  private String notas;

  @Column(nullable = false, precision = 12, scale = 2)
  @Builder.Default
  private BigDecimal total = BigDecimal.ZERO;

  @OneToMany(mappedBy = "pedido", orphanRemoval = true)
  @Builder.Default
  @JsonManagedReference           // ⬅️ evita recursión
  private List<PedidoItem> items = new ArrayList<>();
}
