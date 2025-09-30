package com.sise.mapper;

import com.sise.dto.DetalleVentaDTO;
import com.sise.model.DetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {
    
    public DetalleVentaDTO toDTO(DetalleVenta detalleVenta) {
        if (detalleVenta == null) return null;
        
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalleVenta.getId());
        dto.setCantidad(detalleVenta.getCantidad());
        dto.setPrecioUnitario(detalleVenta.getPrecioUnitario());
        dto.setSubtotal(detalleVenta.getSubtotal());
        
        if (detalleVenta.getVenta() != null) {
            dto.setIdVenta(detalleVenta.getVenta().getId());
        }
        
        if (detalleVenta.getProducto() != null) {
            dto.setIdProducto(detalleVenta.getProducto().getId());
            dto.setProducto(detalleVenta.getProducto().getNombre());
            dto.setImagen(detalleVenta.getProducto().getImagen());
        }
        
        return dto;
    }
    
    public DetalleVenta toEntity(DetalleVentaDTO dto) {
        if (dto == null) return null;
        
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setId(dto.getId());
        detalleVenta.setCantidad(dto.getCantidad());
        detalleVenta.setPrecioUnitario(dto.getPrecioUnitario());
        detalleVenta.setSubtotal(dto.getSubtotal());
        
        return detalleVenta;
    }
}
