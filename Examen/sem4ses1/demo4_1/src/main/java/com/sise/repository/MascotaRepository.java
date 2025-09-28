package com.sise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sise.model.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

@Query(value = "SELECT especie, COUNT(*) FROM mascota GROUP BY especie",
       nativeQuery = true)
List<Object[]> distribucionMascotas();

}
