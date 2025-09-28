package com.sise.repository;

import com.sise.model.Pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p WHERE p.fechaPago BETWEEN :inicio AND :fin")
    BigDecimal sumByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);
}
