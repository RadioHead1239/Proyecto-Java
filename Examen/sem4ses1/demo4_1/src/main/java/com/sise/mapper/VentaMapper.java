package com.sise.mapper;

import com.sise.dto.VentaDTO;
import com.sise.model.Venta;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {
    
    public VentaDTO toDTO(Venta venta) {
        if (venta == null) return null;
        
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado().name());
        dto.setObservaciones(venta.getObservaciones());
        
        if (venta.getCliente() != null) {
            dto.setIdCliente(venta.getCliente().getId());
            dto.setCliente(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
        }
        
        if (venta.getUsuario() != null) {
            dto.setIdUsuario(venta.getUsuario().getId());
            dto.setUsuario(venta.getUsuario().getNombre());
        }
        
        if (venta.getCita() != null) {
            dto.setIdCita(venta.getCita().getId());
            dto.setCita("Cita #" + venta.getCita().getId());
            
            if (venta.getCita().getServicio() != null) {
                dto.setServicio(venta.getCita().getServicio().getNombre());
            }
            
            if (venta.getCita().getMascota() != null) {
                dto.setMascota(venta.getCita().getMascota().getNombre());
            }
        }
        
        return dto;
    }
    
    public Venta toEntity(VentaDTO dto) {
        if (dto == null) return null;
        
        Venta venta = new Venta();
        venta.setId(dto.getId());
        venta.setFecha(dto.getFecha());
        venta.setTotal(dto.getTotal());
        venta.setEstado(Venta.Estado.valueOf(dto.getEstado()));
        venta.setObservaciones(dto.getObservaciones());
        
        if (dto.getIdCliente() != null) {
            com.sise.model.Cliente cliente = new com.sise.model.Cliente();
            cliente.setId(dto.getIdCliente());
            venta.setCliente(cliente);
        }
        
        if (dto.getIdUsuario() != null) {
            com.sise.model.Usuario usuario = new com.sise.model.Usuario();
            usuario.setId(dto.getIdUsuario());
            venta.setUsuario(usuario);
        } else {
            com.sise.model.Usuario usuario = new com.sise.model.Usuario();
            usuario.setId(1L);
            venta.setUsuario(usuario);
        }
        
        if (dto.getIdCita() != null) {
            com.sise.model.Cita cita = new com.sise.model.Cita();
            cita.setId(dto.getIdCita());
            venta.setCita(cita);
        }
        
        return venta;
    }
}