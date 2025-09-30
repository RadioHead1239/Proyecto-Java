package com.sise.repository;

import com.sise.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    
    List<Servicio> findByActivoTrue();
    
    List<Servicio> findByCategoria(String categoria);
    
    List<Servicio> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT s FROM Servicio s WHERE s.nombre LIKE %:termino% OR s.descripcion LIKE %:termino%")
    List<Servicio> buscarPorTermino(@Param("termino") String termino);
    
    @Query("SELECT s FROM Servicio s WHERE s.precio BETWEEN :precioMin AND :precioMax")
    List<Servicio> buscarPorRangoPrecio(@Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);
}
