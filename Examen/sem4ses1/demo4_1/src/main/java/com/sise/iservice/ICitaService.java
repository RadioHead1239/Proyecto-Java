package com.sise.iservice;

import com.sise.dto.CitaDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICitaService {
    
    List<CitaDTO> findAll();
    
    Optional<CitaDTO> findById(Long id);
    
    CitaDTO save(CitaDTO citaDTO);
    
    boolean deleteById(Long id);
    
    List<CitaDTO> findByEstado(String estado);
    
    List<CitaDTO> findByFecha(LocalDateTime fecha);
    
    List<CitaDTO> findByClienteId(Long clienteId);
    
    List<CitaDTO> findCitasPendientesHoy();
    
    Long contarPorEstado(String estado);
}
