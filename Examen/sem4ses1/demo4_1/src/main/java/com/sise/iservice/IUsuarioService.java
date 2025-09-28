package com.sise.iservice;

import java.util.Optional;

import com.sise.dto.UsuarioDTO;


public interface  IUsuarioService {
        Optional<UsuarioDTO> login(String correo, String password);

}
