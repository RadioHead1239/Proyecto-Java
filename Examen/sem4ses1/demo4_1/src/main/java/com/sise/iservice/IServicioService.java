package com.sise.iservice;

import com.sise.dto.ServicioDTO;
import java.util.List;
import java.util.Optional;

public interface IServicioService {
    
    List<ServicioDTO> findAll();
    
    Optional<ServicioDTO> findById(Long id);
    
    ServicioDTO save(ServicioDTO servicioDTO);
    
    boolean deleteById(Long id);
    
    List<ServicioDTO> findByActivos();
    
    List<ServicioDTO> buscarPorTermino(String termino);
    
    List<ServicioDTO> findByCategoria(String categoria);
}
