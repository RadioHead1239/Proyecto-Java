package com.sise.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sise.dto.UsuarioDTO;
import com.sise.iservice.IUsuarioService;
import com.sise.mapper.UsuarioMapper;
import com.sise.model.Usuario;
import com.sise.repository.UsuarioRepository;

@Service
public class UsuarioService implements  IUsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<UsuarioDTO> login(String correo, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoAndPassword(correo, password);
        return usuario.map(UsuarioMapper::toDTO);
    }

    
}
