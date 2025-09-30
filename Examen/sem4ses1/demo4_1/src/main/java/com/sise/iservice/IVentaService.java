package com.sise.iservice;

import com.sise.dto.VentaDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IVentaService {
    
    List<VentaDTO> findAll();
    
    Optional<VentaDTO> findById(Long id);
    
    VentaDTO save(VentaDTO ventaDTO);
    
    boolean deleteById(Long id);
    
    List<VentaDTO> findByClienteId(Long clienteId);
    
    List<VentaDTO> findByFecha(LocalDateTime fecha);
    
    List<VentaDTO> findByEstado(String estado);
    
    Double sumarVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
