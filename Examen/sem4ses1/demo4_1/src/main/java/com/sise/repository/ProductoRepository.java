package com.sise.repository;

import com.sise.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByActivoTrue();
    
    List<Producto> findByCategoria(String categoria);
    
    List<Producto> findByMarca(String marca);
    
    List<Producto> findByStockLessThan(Integer stock);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:termino% OR p.descripcion LIKE %:termino%")
    List<Producto> buscarPorTermino(@Param("termino") String termino);
    
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo")
    List<Producto> findProductosStockBajo();
    
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.activo = true")
    Long contarProductosActivos();
}
