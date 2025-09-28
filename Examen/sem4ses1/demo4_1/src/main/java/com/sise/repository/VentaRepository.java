package com.sise.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sise.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findTop5ByOrderByFechaDesc();

    @Query(value = "SELECT DATE_FORMAT(v.fecha, '%x-W%v') AS semana, SUM(v.total) AS total " +
                   "FROM venta v " +
                   "WHERE v.fecha >= :inicio " +
                   "GROUP BY semana " +
                   "ORDER BY semana",
           nativeQuery = true)
    List<Object[]> ventasPorSemana(@Param("inicio") LocalDateTime inicio);
}
