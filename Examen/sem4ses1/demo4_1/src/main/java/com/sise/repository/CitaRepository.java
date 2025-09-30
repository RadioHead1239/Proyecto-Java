package com.sise.repository;

import com.sise.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    List<Cita> findByEstado(Cita.Estado estado);
    
    List<Cita> findByClienteId(Long clienteId);
    
    List<Cita> findByMascotaId(Long mascotaId);
    
    List<Cita> findByUsuarioId(Long usuarioId);
    
    List<Cita> findByFechaCitaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT c FROM Cita c WHERE c.fechaCita >= :fechaHoy AND c.estado = 'Pendiente'")
    List<Cita> findCitasPendientesHoy(@Param("fechaHoy") LocalDateTime fechaHoy);
    
    @Query("SELECT c FROM Cita c WHERE DATE(c.fechaCita) = DATE(:fecha)")
    List<Cita> findByFecha(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.estado = :estado")
    Long contarPorEstado(@Param("estado") Cita.Estado estado);
    
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.fechaCita BETWEEN :fechaInicio AND :fechaFin")
    Long countByFechaCitaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT c FROM Cita c WHERE c.fechaCita > :fecha ORDER BY c.fechaCita ASC")
    List<Cita> findTop5ByFechaCitaAfterOrderByFechaCitaAsc(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT SUM(s.precio) FROM Cita c JOIN c.servicio s WHERE c.estado = 'Completada' AND c.fechaCita BETWEEN :fechaInicio AND :fechaFin")
    Double calcularIngresosServiciosDelDia(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}