package com.sise.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sise.dto.DashboardDTO;
import com.sise.dto.VentaDTO;
import com.sise.dto.CitaDTO;
import com.sise.iservice.IDashBoardService;

@RestController
public class DashBoardApi {

    private final IDashBoardService dashboardService;

    public DashBoardApi(IDashBoardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard")
    public DashboardDTO getDashboardData() {
        return dashboardService.getDashboardData();
    }

    @GetMapping("/api/dashboard/ventas")
    public List<VentaDTO> getVentasPorFecha(@RequestParam String fecha) {
        System.out.println("API - Fecha recibida para ventas: " + fecha);
        // Parsear la fecha directamente sin conversión de zona horaria
        LocalDate fechaFiltro = LocalDate.parse(fecha);
        System.out.println("API - Fecha parseada: " + fechaFiltro);
        return dashboardService.getVentasPorFecha(fechaFiltro);
    }

    @GetMapping("/api/dashboard/citas")
    public List<CitaDTO> getCitasPorFecha(@RequestParam String fecha) {
        System.out.println("API - Fecha recibida para citas: " + fecha);
        // Parsear la fecha directamente sin conversión de zona horaria
        LocalDate fechaFiltro = LocalDate.parse(fecha);
        System.out.println("API - Fecha parseada: " + fechaFiltro);
        return dashboardService.getCitasPorFecha(fechaFiltro);
    }

    @GetMapping("/api/dashboard/estadisticas")
    public DashboardDTO getEstadisticasPorFecha(@RequestParam String fecha) {
        System.out.println("API - Fecha recibida para estadísticas: " + fecha);
        // Parsear la fecha directamente sin conversión de zona horaria
        LocalDate fechaFiltro = LocalDate.parse(fecha);
        System.out.println("API - Fecha parseada: " + fechaFiltro);
        return dashboardService.getEstadisticasPorFecha(fechaFiltro);
    }
}
