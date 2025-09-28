package com.sise.mapper;

import com.sise.dto.MascotaDistribucionDTO;

public class MascotaMapper {
    public static MascotaDistribucionDTO toDistribucionDTO(Object[] row) {
        String especie = (String) row[0];
        Long cantidad = ((Number) row[1]).longValue();
        return new MascotaDistribucionDTO(especie, cantidad);
    }
}
