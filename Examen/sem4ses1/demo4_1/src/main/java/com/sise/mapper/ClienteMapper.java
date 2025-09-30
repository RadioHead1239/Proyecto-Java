package com.sise.mapper;

import com.sise.dto.ClienteDTO;
import com.sise.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    
    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;
        
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreo(cliente.getCorreo());
        dto.setDireccion(cliente.getDireccion());
        dto.setImagen(cliente.getImagen());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        
        if (cliente.getMascotas() != null) {
            dto.setTotalMascotas(cliente.getMascotas().size());
        }
        if (cliente.getCitas() != null) {
            dto.setTotalCitas(cliente.getCitas().size());
        }
        
        return dto;
    }
    
    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        cliente.setDireccion(dto.getDireccion());
        cliente.setImagen(dto.getImagen());
        cliente.setFechaRegistro(dto.getFechaRegistro());
        
        return cliente;
    }
}