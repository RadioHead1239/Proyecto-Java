package com.sise.iservice;

import java.util.List;

import com.sise.dto.ClienteDTO;

public interface IClienteService {
    List<ClienteDTO> listar();
    ClienteDTO buscarPorId(Long id);
    ClienteDTO registrar(ClienteDTO cliente);
    ClienteDTO actualizar(Long id, ClienteDTO cliente);
    void eliminar(Long id);
}
