package com.sise.iservice;

import java.util.List;
import java.util.Optional;

import com.sise.dto.UsuarioDTO;

public interface IUsuarioService {
    
    List<UsuarioDTO> findAll();
    
    Optional<UsuarioDTO> findById(Long id);
    
    UsuarioDTO save(UsuarioDTO usuarioDTO);
    
    boolean deleteById(Long id);
    
    Optional<UsuarioDTO> login(String correo, String password);
    
}
