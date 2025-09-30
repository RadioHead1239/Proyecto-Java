package com.sise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.ProductoDTO;
import com.sise.iservice.IProductoService;
import com.sise.mapper.ProductoMapper;
import com.sise.model.Producto;
import com.sise.repository.ProductoRepository;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDTO> findAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDTO> findById(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDTO);
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(savedProducto);
    }

    @Override
    public boolean deleteById(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProductoDTO> findByActivos() {
        List<Producto> productos = productoRepository.findByActivoTrue();
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> buscarPorTermino(String termino) {
        List<Producto> productos = productoRepository.buscarPorTermino(termino);
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> findByCategoria(String categoria) {
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> findProductosStockBajo() {
        List<Producto> productos = productoRepository.findProductosStockBajo();
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
