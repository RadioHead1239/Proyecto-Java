package com.sise.repository;

import com.sise.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    List<Venta> findByClienteId(Long clienteId);
    
    List<Venta> findByUsuarioId(Long usuarioId);
    
    List<Venta> findByEstado(Venta.Estado estado);
    
    List<Venta> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT v FROM Venta v WHERE DATE(v.fecha) = DATE(:fecha)")
    List<Venta> findByFecha(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.estado = 'Completada' AND v.fecha BETWEEN :fechaInicio AND :fechaFin")
    Double sumarVentasPorPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COUNT(v) FROM Venta v WHERE v.estado = :estado")
    Long contarPorEstado(@Param("estado") Venta.Estado estado);
    
    @Query("SELECT v FROM Venta v ORDER BY v.fecha DESC")
    List<Venta> findTop5ByOrderByFechaDesc();
    
    @Query("SELECT v FROM Venta v WHERE v.fecha >= :fecha ORDER BY v.fecha DESC")
    List<Venta> ventasPorSemana(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT v FROM Venta v WHERE v.cita IS NOT NULL ORDER BY v.fecha DESC")
    List<Venta> findVentasGeneradasPorCitas();
    
    @Query("SELECT v FROM Venta v WHERE v.cita IS NULL ORDER BY v.fecha DESC")
    List<Venta> findVentasRegulares();
    
    @Query("SELECT DATE(v.fecha) as fecha, SUM(v.total) as total " +
           "FROM Venta v WHERE v.estado = 'Completada' AND v.fecha >= :fechaInicio " +
           "GROUP BY DATE(v.fecha) ORDER BY DATE(v.fecha)")
    List<Object[]> ingresosPorSemana(@Param("fechaInicio") LocalDateTime fechaInicio);
}