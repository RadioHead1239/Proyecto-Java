package com.sise.repository;

import com.sise.model.Mascota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

    @Query(value = "SELECT especie, COUNT(*) FROM Mascota GROUP BY especie", nativeQuery = true)
    List<Object[]> distribucionMascotas();

}
