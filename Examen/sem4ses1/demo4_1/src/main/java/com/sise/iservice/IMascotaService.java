package com.sise.iservice;

import java.util.List;
import java.util.Optional;

import com.sise.dto.MascotaDTO;

public interface IMascotaService {
    
    List<MascotaDTO> findAll();
    
    Optional<MascotaDTO> findById(Long id);
    
    MascotaDTO save(MascotaDTO mascotaDTO);
    
    boolean deleteById(Long id);
    
    List<MascotaDTO> findByClienteId(Long clienteId);
    
    List<MascotaDTO> findByEspecie(String especie);
}
