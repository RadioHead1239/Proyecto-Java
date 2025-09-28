package com.sise.service;

import com.sise.model.Cita;
import com.sise.repository.CitaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CitaService {

    private final CitaRepository citaRepository;

    @Transactional(readOnly = true)
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    public void delete(Long id) {
        citaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countPendientesHoy() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return citaRepository.countByFechaCitaBetween(start, end);
    }

    @Transactional(readOnly = true)
    public List<Cita> proximasCitas() {
        return citaRepository.findTop5ByFechaCitaAfterOrderByFechaCitaAsc(LocalDateTime.now());
    }
}
