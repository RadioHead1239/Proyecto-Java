package com.sise.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sise.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    long countByFechaCitaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Cita> findTop5ByFechaCitaAfterOrderByFechaCitaAsc(LocalDateTime now);
}
