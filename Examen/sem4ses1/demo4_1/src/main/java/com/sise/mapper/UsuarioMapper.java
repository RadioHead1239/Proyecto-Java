package com.sise.mapper;

import com.sise.dto.UsuarioDTO;
import com.sise.model.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setCorreo(u.getCorreo());
        dto.setRol(u.getRol());
        return dto;
    }
}