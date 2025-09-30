package com.sise.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.CitaDTO;
import com.sise.iservice.ICitaService;
import com.sise.mapper.CitaMapper;
import com.sise.model.Cita;
import com.sise.repository.CitaRepository;
import com.sise.service.VentaService;

@Service
public class CitaService implements ICitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;
    private final VentaService ventaService;

    public CitaService(CitaRepository citaRepository, CitaMapper citaMapper, VentaService ventaService) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
        this.ventaService = ventaService;
    }

    @Override
    public List<CitaDTO> findAll() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CitaDTO> findById(Long id) {
        return citaRepository.findById(id)
                .map(citaMapper::toDTO);
    }

    @Override
    public CitaDTO save(CitaDTO citaDTO) {
        Cita cita = citaMapper.toEntity(citaDTO);
        
        if (cita.getId() != null) {
            Optional<Cita> citaExistente = citaRepository.findById(cita.getId());
            if (citaExistente.isPresent()) {
                Cita citaAnterior = citaExistente.get();
                if (!citaAnterior.getEstado().equals(Cita.Estado.Completada) && 
                    cita.getEstado().equals(Cita.Estado.Completada)) {
                    Cita savedCita = citaRepository.save(cita);
                    this.ventaService.crearVentaDesdeCita(savedCita);
                    return citaMapper.toDTO(savedCita);
                }
            }
        }
        
        Cita savedCita = citaRepository.save(cita);
        return citaMapper.toDTO(savedCita);
    }

    @Override
    public boolean deleteById(Long id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CitaDTO> findByEstado(String estado) {
        List<Cita> citas = citaRepository.findByEstado(Cita.Estado.valueOf(estado));
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> findByFecha(LocalDateTime fecha) {
        List<Cita> citas = citaRepository.findByFecha(fecha);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> findByClienteId(Long clienteId) {
        List<Cita> citas = citaRepository.findByClienteId(clienteId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> findCitasPendientesHoy() {
        List<Cita> citas = citaRepository.findCitasPendientesHoy(LocalDateTime.now());
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long contarPorEstado(String estado) {
        return citaRepository.contarPorEstado(Cita.Estado.valueOf(estado));
    }
}
