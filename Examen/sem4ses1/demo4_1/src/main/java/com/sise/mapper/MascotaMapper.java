package com.sise.mapper;

import com.sise.dto.MascotaDTO;
import com.sise.model.Mascota;
import org.springframework.stereotype.Component;

@Component
public class MascotaMapper {
    
    public MascotaDTO toDTO(Mascota mascota) {
        if (mascota == null) return null;
        
        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie().name());
        dto.setRaza(mascota.getRaza());
        dto.setEdad(mascota.getEdad());
        dto.setPeso(mascota.getPeso());
        dto.setObservaciones(mascota.getObservaciones());
        dto.setImagen(mascota.getImagen());
        
        if (mascota.getCliente() != null) {
            dto.setIdCliente(mascota.getCliente().getId());
            dto.setCliente(mascota.getCliente().getNombre() + " " + mascota.getCliente().getApellido());
        }
        
        return dto;
    }
    
    public Mascota toEntity(MascotaDTO dto) {
        if (dto == null) return null;
        
        Mascota mascota = new Mascota();
        mascota.setId(dto.getId());
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(Mascota.Especie.valueOf(dto.getEspecie()));
        mascota.setRaza(dto.getRaza());
        mascota.setEdad(dto.getEdad());
        mascota.setPeso(dto.getPeso());
        mascota.setObservaciones(dto.getObservaciones());
        mascota.setImagen(dto.getImagen());
        
        if (dto.getIdCliente() != null) {
            com.sise.model.Cliente cliente = new com.sise.model.Cliente();
            cliente.setId(dto.getIdCliente());
            mascota.setCliente(cliente);
        }
        
        return mascota;
    }
}