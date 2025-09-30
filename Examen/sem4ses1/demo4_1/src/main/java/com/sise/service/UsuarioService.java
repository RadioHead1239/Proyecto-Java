package com.sise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sise.dto.UsuarioDTO;
import com.sise.iservice.IUsuarioService;
import com.sise.mapper.UsuarioMapper;
import com.sise.model.Usuario;
import com.sise.repository.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO);
    }

    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        } else if (usuario.getId() != null) {
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                usuario.setPassword(usuarioExistente.getPassword());
            }
        }
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    @Override
    public boolean deleteById(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<UsuarioDTO> login(String correo, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoActivo(correo);
        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            return usuario.map(usuarioMapper::toDTO);
        }
        return Optional.empty();
    }
}
