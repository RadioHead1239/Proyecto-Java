package com.sise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.ServicioDTO;
import com.sise.iservice.IServicioService;
import com.sise.mapper.ServicioMapper;
import com.sise.model.Servicio;
import com.sise.repository.ServicioRepository;

@Service
public class ServicioService implements IServicioService {

    private final ServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;

    public ServicioService(ServicioRepository servicioRepository, ServicioMapper servicioMapper) {
        this.servicioRepository = servicioRepository;
        this.servicioMapper = servicioMapper;
    }

    @Override
    public List<ServicioDTO> findAll() {
        List<Servicio> servicios = servicioRepository.findAll();
        return servicios.stream()
                .map(servicioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServicioDTO> findById(Long id) {
        return servicioRepository.findById(id)
                .map(servicioMapper::toDTO);
    }

    @Override
    public ServicioDTO save(ServicioDTO servicioDTO) {
        Servicio servicio = servicioMapper.toEntity(servicioDTO);
        Servicio savedServicio = servicioRepository.save(servicio);
        return servicioMapper.toDTO(savedServicio);
    }

    @Override
    public boolean deleteById(Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ServicioDTO> findByActivos() {
        List<Servicio> servicios = servicioRepository.findByActivoTrue();
        return servicios.stream()
                .map(servicioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServicioDTO> buscarPorTermino(String termino) {
        List<Servicio> servicios = servicioRepository.buscarPorTermino(termino);
        return servicios.stream()
                .map(servicioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServicioDTO> findByCategoria(String categoria) {
        List<Servicio> servicios = servicioRepository.findByCategoria(categoria);
        return servicios.stream()
                .map(servicioMapper::toDTO)
                .collect(Collectors.toList());
    }
}
