package com.sise.mapper;

import com.sise.dto.ServicioDTO;
import com.sise.model.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {
    
    public ServicioDTO toDTO(Servicio servicio) {
        if (servicio == null) return null;
        
        ServicioDTO dto = new ServicioDTO();
        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setPrecio(servicio.getPrecio());
        dto.setDuracionMinutos(servicio.getDuracionMinutos());
        dto.setImagen(servicio.getImagen());
        dto.setCategoria(servicio.getCategoria());
        dto.setActivo(servicio.getActivo());
        
        if (servicio.getCitas() != null) {
            dto.setTotalCitas(servicio.getCitas().size());
        }
        
        return dto;
    }
    
    public Servicio toEntity(ServicioDTO dto) {
        if (dto == null) return null;
        
        Servicio servicio = new Servicio();
        servicio.setId(dto.getId());
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setDuracionMinutos(dto.getDuracionMinutos());
        servicio.setImagen(dto.getImagen());
        servicio.setCategoria(dto.getCategoria());
        servicio.setActivo(dto.getActivo());
        
        return servicio;
    }
}
