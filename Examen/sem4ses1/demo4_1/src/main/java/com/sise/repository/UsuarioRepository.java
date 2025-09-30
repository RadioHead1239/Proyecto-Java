package com.sise.repository;

import com.sise.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByCorreo(String correo);
    
    List<Usuario> findByRol(Usuario.Rol rol);
    
    List<Usuario> findByEstadoTrue();
    
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.estado = true")
    Optional<Usuario> findByCorreoActivo(@Param("correo") String correo);
    
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
    Long contarPorRol(@Param("rol") Usuario.Rol rol);
}