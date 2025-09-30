package com.sise.iservice;

import com.sise.dto.ClienteDTO;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    
    List<ClienteDTO> findAll();
    
    Optional<ClienteDTO> findById(Long id);
    
    ClienteDTO save(ClienteDTO clienteDTO);
    
    boolean deleteById(Long id);
    
    List<ClienteDTO> buscarPorTermino(String termino);
    
    Long contarTotalClientes();
    
    Optional<ClienteDTO> findByCorreo(String correo);
}