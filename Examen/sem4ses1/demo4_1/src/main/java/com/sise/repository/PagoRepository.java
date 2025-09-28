package com.sise.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sise.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p WHERE p.fechaPago BETWEEN :inicio AND :fin")
    BigDecimal sumByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);
}
