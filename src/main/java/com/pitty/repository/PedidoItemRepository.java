// src/main/java/com/pitty/repository/PedidoItemRepository.java
package com.pitty.repository;

import com.pitty.domain.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    boolean existsByPostreId(Long postreId);
}
