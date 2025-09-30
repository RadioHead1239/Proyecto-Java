package com.sise.repository;

import com.sise.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCorreo(String correo);
    
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);
    
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:termino% OR c.apellido LIKE %:termino% OR c.correo LIKE %:termino%")
    List<Cliente> buscarPorTermino(@Param("termino") String termino);
    
    @Query("SELECT COUNT(c) FROM Cliente c")
    Long contarTotalClientes();
    
    @Query("SELECT c FROM Cliente c ORDER BY c.fechaRegistro DESC")
    List<Cliente> findAllOrderByFechaRegistroDesc();
}