package com.sise.mapper;

import com.sise.dto.ClienteDTO;
import com.sise.model.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getIdCliente());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setTelefono(c.getTelefono());
        dto.setCorreo(c.getCorreo());
        dto.setFechaRegistro(c.getFechaRegistro());
        return dto;
    }

    public static Cliente toEntity(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setIdCliente(dto.getId());
        c.setNombre(dto.getNombre());
        c.setApellido(dto.getApellido());
        c.setTelefono(dto.getTelefono());
        c.setCorreo(dto.getCorreo());
        c.setFechaRegistro(dto.getFechaRegistro());
        return c;
    }
}
