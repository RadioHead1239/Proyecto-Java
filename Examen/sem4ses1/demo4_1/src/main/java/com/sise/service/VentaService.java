package com.sise.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sise.dto.VentaDTO;
import com.sise.dto.DetalleVentaDTO;
import com.sise.iservice.IVentaService;
import com.sise.mapper.VentaMapper;
import com.sise.model.Venta;
import com.sise.model.DetalleVenta;
import com.sise.model.Producto;
import com.sise.model.Cita;
import com.sise.repository.VentaRepository;
import com.sise.repository.ProductoRepository;
import com.sise.repository.DetalleVentaRepository;

@Service
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaMapper ventaMapper;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository, 
                       DetalleVentaRepository detalleVentaRepository, VentaMapper ventaMapper) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaMapper = ventaMapper;
    }

    @Override
    public List<VentaDTO> findAll() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VentaDTO> findById(Long id) {
        return ventaRepository.findById(id)
                .map(ventaMapper::toDTO);
    }

    @Override
    @Transactional
    public VentaDTO save(VentaDTO ventaDTO) {
        if (ventaDTO.getEstado().equals("Completada") && ventaDTO.getDetalles() != null && !ventaDTO.getDetalles().isEmpty()) {
            for (DetalleVentaDTO detalle : ventaDTO.getDetalles()) {
                Optional<Producto> productoOpt = productoRepository.findById(detalle.getIdProducto());
                if (productoOpt.isEmpty()) {
                    throw new RuntimeException("Producto no encontrado: " + detalle.getIdProducto());
                }
                
                Producto producto = productoOpt.get();
                if (producto.getStock() < detalle.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() + 
                        ". Stock disponible: " + producto.getStock() + ", solicitado: " + detalle.getCantidad());
                }
            }
        }
        
        Venta venta = ventaMapper.toEntity(ventaDTO);
        Venta savedVenta = ventaRepository.save(venta);
        if (ventaDTO.getDetalles() != null && !ventaDTO.getDetalles().isEmpty()) {
            if (ventaDTO.getId() != null) {
                List<DetalleVenta> detallesExistentes = detalleVentaRepository.findByVentaId(ventaDTO.getId());
                detalleVentaRepository.deleteAll(detallesExistentes);
            }
            
            for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
                DetalleVenta detalle = ventaMapper.detalleToEntity(detalleDTO);
                detalle.setVenta(savedVenta);
                detalleVentaRepository.save(detalle);
                if (savedVenta.getEstado() == Venta.Estado.Completada) {
                    Optional<Producto> productoOpt = productoRepository.findById(detalleDTO.getIdProducto());
                    if (productoOpt.isPresent()) {
                        Producto producto = productoOpt.get();
                        producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                        productoRepository.save(producto);
                    }
                }
            }
        }
        
        return ventaMapper.toDTO(savedVenta);
    }

    @Override
    public boolean deleteById(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<VentaDTO> findByClienteId(Long clienteId) {
        List<Venta> ventas = ventaRepository.findByClienteId(clienteId);
        return ventas.stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaDTO> findByFecha(LocalDateTime fecha) {
        List<Venta> ventas = ventaRepository.findByFecha(fecha);
        return ventas.stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaDTO> findByEstado(String estado) {
        List<Venta> ventas = ventaRepository.findByEstado(Venta.Estado.valueOf(estado));
        return ventas.stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Double sumarVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Double total = ventaRepository.sumarVentasPorPeriodo(fechaInicio, fechaFin);
        return total != null ? total : 0.0;
    }
    
    public VentaDTO crearVentaDesdeCita(Cita cita) {
        Venta venta = new Venta();
        venta.setCliente(cita.getCliente());
        venta.setUsuario(cita.getUsuario());
        venta.setCita(cita);
        venta.setTotal(cita.getServicio().getPrecio());
        venta.setEstado(Venta.Estado.Completada);
        venta.setObservaciones("Venta generada automáticamente por servicio: " + cita.getServicio().getNombre());
        venta.setFecha(cita.getFechaCita());
        
        Venta savedVenta = ventaRepository.save(venta);
        return ventaMapper.toDTO(savedVenta);
    }
}
