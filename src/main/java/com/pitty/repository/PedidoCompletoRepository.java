package com.pitty.repository;

import com.pitty.domain.PedidoCompleto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface PedidoCompletoRepository extends JpaRepository<PedidoCompleto, Long> {

    Page<PedidoCompleto> findByClienteId(Long clienteId, Pageable pageable);

    Page<PedidoCompleto> findByPostreId(Long postreId, Pageable pageable);

    Page<PedidoCompleto> findByFechaEntregaBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
