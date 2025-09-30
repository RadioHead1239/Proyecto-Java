package com.sise.iservice;

import com.sise.dto.PagoDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPagoService {
    
    List<PagoDTO> findAll();
    
    Optional<PagoDTO> findById(Long id);
    
    PagoDTO save(PagoDTO pagoDTO);
    
    boolean deleteById(Long id);
    
    List<PagoDTO> findByCitaId(Long citaId);
    
    List<PagoDTO> findByEstado(String estado);
    
    List<PagoDTO> findByFecha(LocalDateTime fecha);
    
    Double sumarIngresosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
