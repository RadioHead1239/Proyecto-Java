package com.sise.service;

import com.sise.model.Mascota;
import com.sise.repository.MascotaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    @Transactional(readOnly = true)
    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Mascota> findById(Integer id) {
        return mascotaRepository.findById(id);
    }

    public Mascota save(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public void delete(Integer id) {
        mascotaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Object[]> getDistribucionPorEspecie() {
        return mascotaRepository.distribucionMascotas();
    }
}
