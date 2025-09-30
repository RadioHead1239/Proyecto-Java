package com.sise.mapper;

import com.sise.dto.ProductoDTO;
import com.sise.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    
    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;
        
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setStockMinimo(producto.getStockMinimo());
        dto.setImagen(producto.getImagen());
        dto.setCategoria(producto.getCategoria());
        dto.setMarca(producto.getMarca());
        dto.setActivo(producto.getActivo());
        dto.setFechaCreacion(producto.getFechaCreacion());
        
        if (producto.getDetalleVentas() != null) {
            dto.setTotalVendido(producto.getDetalleVentas().stream()
                    .mapToInt(dv -> dv.getCantidad())
                    .sum());
        }
        
        return dto;
    }
    
    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;
        
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setImagen(dto.getImagen());
        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setActivo(dto.getActivo());
        producto.setFechaCreacion(dto.getFechaCreacion());
        
        return producto;
    }
}
