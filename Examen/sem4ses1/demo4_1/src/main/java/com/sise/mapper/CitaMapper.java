package com.sise.mapper;

import com.sise.dto.CitaDTO;
import com.sise.model.Cita;

public class CitaMapper {
    public static CitaDTO toDTO(Cita c) {
        CitaDTO dto = new CitaDTO();
        dto.setId(c.getIdCita());
        dto.setFechaCita(c.getFechaCita());
        dto.setCliente(c.getCliente().getNombre() + " " + c.getCliente().getApellido());
        dto.setMascota(c.getMascota().getNombre());
        dto.setServicio(c.getServicio().getNombre());
        dto.setEstado(c.getEstado());
        return dto;
    }
}
