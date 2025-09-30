package com.sise.mapper;

import com.sise.dto.PagoDTO;
import com.sise.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    
    public PagoDTO toDTO(Pago pago) {
        if (pago == null) return null;
        
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMetodoPago(pago.getMetodoPago().name());
        dto.setEstado(pago.getEstado().name());
        dto.setObservaciones(pago.getObservaciones());
        
        if (pago.getCita() != null) {
            dto.setIdCita(pago.getCita().getId());
            if (pago.getCita().getCliente() != null) {
                dto.setCliente(pago.getCita().getCliente().getNombre() + " " + pago.getCita().getCliente().getApellido());
            }
            if (pago.getCita().getServicio() != null) {
                dto.setServicio(pago.getCita().getServicio().getNombre());
            }
        }
        
        return dto;
    }
    
    public Pago toEntity(PagoDTO dto) {
        if (dto == null) return null;
        
        Pago pago = new Pago();
        pago.setId(dto.getId());
        pago.setMonto(dto.getMonto());
        pago.setFechaPago(dto.getFechaPago());
        pago.setMetodoPago(Pago.MetodoPago.valueOf(dto.getMetodoPago()));
        pago.setEstado(Pago.Estado.valueOf(dto.getEstado()));
        pago.setObservaciones(dto.getObservaciones());
        
        if (dto.getIdCita() != null) {
            com.sise.model.Cita cita = new com.sise.model.Cita();
            cita.setId(dto.getIdCita());
            pago.setCita(cita);
        }
        
        return pago;
    }
}
