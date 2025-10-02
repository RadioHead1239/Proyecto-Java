package com.sise.mapper;

import com.sise.dto.VentaDTO;
import com.sise.dto.DetalleVentaDTO;
import com.sise.model.Venta;
import com.sise.model.DetalleVenta;
import com.sise.model.Producto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

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
        
        // Mapear detalles de venta
        if (venta.getDetalleVentas() != null && !venta.getDetalleVentas().isEmpty()) {
            List<DetalleVentaDTO> detallesDTO = venta.getDetalleVentas().stream()
                .map(this::detalleToDTO)
                .collect(Collectors.toList());
            dto.setDetalles(detallesDTO);
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
    
    public DetalleVentaDTO detalleToDTO(DetalleVenta detalle) {
        if (detalle == null) return null;
        
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        
        if (detalle.getVenta() != null) {
            dto.setIdVenta(detalle.getVenta().getId());
        }
        
        if (detalle.getProducto() != null) {
            dto.setIdProducto(detalle.getProducto().getId());
            dto.setProducto(detalle.getProducto().getNombre());
            dto.setImagen(detalle.getProducto().getImagen());
        }
        
        return dto;
    }
    
    public DetalleVenta detalleToEntity(DetalleVentaDTO dto) {
        if (dto == null) return null;
        
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(dto.getId());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());
        
        if (dto.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setId(dto.getIdProducto());
            detalle.setProducto(producto);
        }
        
        return detalle;
    }
}