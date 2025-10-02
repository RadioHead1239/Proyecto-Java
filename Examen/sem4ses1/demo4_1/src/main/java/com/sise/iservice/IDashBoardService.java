package com.sise.iservice;

import java.time.LocalDate;
import java.util.List;

import com.sise.dto.DashboardDTO;
import com.sise.dto.VentaDTO;
import com.sise.dto.CitaDTO;

public interface IDashBoardService {
    DashboardDTO getDashboardData();
    List<VentaDTO> getVentasPorFecha(LocalDate fecha);
    List<CitaDTO> getCitasPorFecha(LocalDate fecha);
    DashboardDTO getEstadisticasPorFecha(LocalDate fecha);
}
