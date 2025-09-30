package com.sise.iservice;

import com.sise.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;

public interface IProductoService {
    
    List<ProductoDTO> findAll();
    
    Optional<ProductoDTO> findById(Long id);
    
    ProductoDTO save(ProductoDTO productoDTO);
    
    boolean deleteById(Long id);
    
    List<ProductoDTO> findByActivos();
    
    List<ProductoDTO> buscarPorTermino(String termino);
    
    List<ProductoDTO> findByCategoria(String categoria);
    
    List<ProductoDTO> findProductosStockBajo();
}
