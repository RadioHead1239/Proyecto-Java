package com.sise.repository;

import com.sise.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    
    List<DetalleVenta> findByVentaId(Long ventaId);
    
    List<DetalleVenta> findByProductoId(Long productoId);
    
    @Query("SELECT d FROM DetalleVenta d WHERE d.venta.id = :ventaId")
    List<DetalleVenta> findByVentaIdWithDetails(@Param("ventaId") Long ventaId);
    
    @Query("SELECT SUM(d.cantidad) FROM DetalleVenta d WHERE d.producto.id = :productoId")
    Long sumCantidadByProductoId(@Param("productoId") Long productoId);
}