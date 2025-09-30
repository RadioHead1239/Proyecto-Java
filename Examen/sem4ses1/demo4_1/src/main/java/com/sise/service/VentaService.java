package com.sise.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.VentaDTO;
import com.sise.iservice.IVentaService;
import com.sise.mapper.VentaMapper;
import com.sise.model.Venta;
import com.sise.model.Cita;
import com.sise.repository.VentaRepository;

@Service
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    public VentaService(VentaRepository ventaRepository, VentaMapper ventaMapper) {
        this.ventaRepository = ventaRepository;
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
    public VentaDTO save(VentaDTO ventaDTO) {
        Venta venta = ventaMapper.toEntity(ventaDTO);
        Venta savedVenta = ventaRepository.save(venta);
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
    
    /**
     * Crea una venta automáticamente cuando una cita se completa
     */
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
