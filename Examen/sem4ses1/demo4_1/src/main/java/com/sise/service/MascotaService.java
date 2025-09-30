package com.sise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.MascotaDTO;
import com.sise.iservice.IMascotaService;
import com.sise.mapper.MascotaMapper;
import com.sise.model.Mascota;
import com.sise.repository.MascotaRepository;

@Service
public class MascotaService implements IMascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;

    public MascotaService(MascotaRepository mascotaRepository, MascotaMapper mascotaMapper) {
        this.mascotaRepository = mascotaRepository;
        this.mascotaMapper = mascotaMapper;
    }

    @Override
    public List<MascotaDTO> findAll() {
        List<Mascota> mascotas = mascotaRepository.findAll();
        return mascotas.stream()
                .map(mascotaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MascotaDTO> findById(Long id) {
        return mascotaRepository.findById(id)
                .map(mascotaMapper::toDTO);
    }

    @Override
    public MascotaDTO save(MascotaDTO mascotaDTO) {
        Mascota mascota = mascotaMapper.toEntity(mascotaDTO);
        Mascota savedMascota = mascotaRepository.save(mascota);
        return mascotaMapper.toDTO(savedMascota);
    }

    @Override
    public boolean deleteById(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<MascotaDTO> findByClienteId(Long clienteId) {
        List<Mascota> mascotas = mascotaRepository.findByClienteId(clienteId);
        return mascotas.stream()
                .map(mascotaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MascotaDTO> findByEspecie(String especie) {
        List<Mascota> mascotas = mascotaRepository.findByEspecie(Mascota.Especie.valueOf(especie));
        return mascotas.stream()
                .map(mascotaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
