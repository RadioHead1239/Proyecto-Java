package com.sise.repository;

import com.sise.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    List<Pago> findByCitaId(Long citaId);
    
    List<Pago> findByEstado(Pago.Estado estado);
    
    List<Pago> findByMetodoPago(Pago.MetodoPago metodoPago);
    
    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = 'Completado' AND p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    Double sumarIngresosPorPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = 'Completado' AND p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    Double sumByFechaPagoBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.estado = :estado")
    Long contarPorEstado(@Param("estado") Pago.Estado estado);
    
    @Query("SELECT DATE(p.fechaPago) as fecha, SUM(p.monto) as total " +
           "FROM Pago p WHERE p.estado = 'Completado' AND p.fechaPago >= :fechaInicio " +
           "GROUP BY DATE(p.fechaPago) ORDER BY DATE(p.fechaPago)")
    List<Object[]> ingresosPorSemana(@Param("fechaInicio") LocalDateTime fechaInicio);
}