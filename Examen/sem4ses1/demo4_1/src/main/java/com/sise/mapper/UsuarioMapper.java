package com.sise.mapper;

import com.sise.dto.UsuarioDTO;
import com.sise.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol().name());
        dto.setImagen(usuario.getImagen());
        dto.setEstado(usuario.getEstado());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        
        if (usuario.getCitas() != null) {
            dto.setTotalCitas(usuario.getCitas().size());
        }
        if (usuario.getVentas() != null) {
            dto.setTotalVentas(usuario.getVentas().size());
        }
        
        return dto;
    }
    
    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword()); // Agregar contraseña
        usuario.setRol(Usuario.Rol.valueOf(dto.getRol()));
        usuario.setImagen(dto.getImagen());
        usuario.setEstado(dto.getEstado());
        usuario.setFechaCreacion(dto.getFechaCreacion());
        
        return usuario;
    }
}