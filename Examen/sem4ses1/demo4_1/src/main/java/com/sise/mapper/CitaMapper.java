package com.sise.mapper;

import com.sise.dto.CitaDTO;
import com.sise.model.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {
    
    public CitaDTO toDTO(Cita cita) {
        if (cita == null) return null;
        
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setFechaCita(cita.getFechaCita());
        dto.setEstado(cita.getEstado().name());
        dto.setObservaciones(cita.getObservaciones());
        
        if (cita.getCliente() != null) {
            dto.setIdCliente(cita.getCliente().getId());
            dto.setCliente(cita.getCliente().getNombre() + " " + cita.getCliente().getApellido());
        }
        
        if (cita.getMascota() != null) {
            dto.setIdMascota(cita.getMascota().getId());
            dto.setMascota(cita.getMascota().getNombre());
        }
        
        if (cita.getServicio() != null) {
            dto.setIdServicio(cita.getServicio().getId());
            dto.setServicio(cita.getServicio().getNombre());
            dto.setPrecioServicio(cita.getServicio().getPrecio());
        }
        
        if (cita.getUsuario() != null) {
            dto.setIdUsuario(cita.getUsuario().getId());
            dto.setUsuario(cita.getUsuario().getNombre());
        }
        
        if (cita.getVenta() != null) {
            dto.setIdVenta(cita.getVenta().getId());
            dto.setVenta("Venta #" + cita.getVenta().getId());
            dto.setTieneVenta(true);
        } else {
            dto.setTieneVenta(false);
        }
        
        return dto;
    }
    
    public Cita toEntity(CitaDTO dto) {
        if (dto == null) return null;
        
        Cita cita = new Cita();
        cita.setId(dto.getId());
        cita.setFechaCita(dto.getFechaCita());
        cita.setEstado(Cita.Estado.valueOf(dto.getEstado()));
        cita.setObservaciones(dto.getObservaciones());
        
        if (dto.getIdCliente() != null) {
            com.sise.model.Cliente cliente = new com.sise.model.Cliente();
            cliente.setId(dto.getIdCliente());
            cita.setCliente(cliente);
        }
        
        if (dto.getIdMascota() != null) {
            com.sise.model.Mascota mascota = new com.sise.model.Mascota();
            mascota.setId(dto.getIdMascota());
            cita.setMascota(mascota);
        }
        
        if (dto.getIdServicio() != null) {
            com.sise.model.Servicio servicio = new com.sise.model.Servicio();
            servicio.setId(dto.getIdServicio());
            cita.setServicio(servicio);
        }
        
        if (dto.getIdUsuario() != null) {
            com.sise.model.Usuario usuario = new com.sise.model.Usuario();
            usuario.setId(dto.getIdUsuario());
            cita.setUsuario(usuario);
        }
        
        return cita;
    }
}