package com.sise.repository;

import com.sise.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    
    List<Mascota> findByClienteId(Long clienteId);
    
    List<Mascota> findByEspecie(Mascota.Especie especie);
    
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT m FROM Mascota m WHERE m.cliente.id = :clienteId")
    List<Mascota> findByClienteIdWithDetails(@Param("clienteId") Long clienteId);
    
    @Query("SELECT COUNT(m) FROM Mascota m WHERE m.especie = :especie")
    Long contarPorEspecie(@Param("especie") Mascota.Especie especie);
    
    @Query("SELECT m FROM Mascota m WHERE m.nombre LIKE %:termino% OR m.raza LIKE %:termino%")
    List<Mascota> buscarPorTermino(@Param("termino") String termino);
    
    @Query("SELECT m.especie, COUNT(m) FROM Mascota m GROUP BY m.especie")
    List<Object[]> distribucionMascotas();
}