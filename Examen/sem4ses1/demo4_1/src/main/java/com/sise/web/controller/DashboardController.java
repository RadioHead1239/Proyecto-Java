package com.sise.web.controller;

import com.sise.service.DashboardService;
import com.sise.service.MascotaService;
import com.sise.service.VentaService;
import com.sise.web.dto.DashboardSummary;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final MascotaService mascotaService;
    private final VentaService ventaService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        DashboardSummary summary = dashboardService.obtenerResumen();
        model.addAttribute("summary", summary);

        List<Map<String, Object>> mascotas = mascotaService.getDistribucionPorEspecie().stream()
                .map(row -> Map.<String, Object>of(
                        "especie", row[0],
                        "total", row[1]))
                .collect(Collectors.toList());
        model.addAttribute("mascotasDistribucion", mascotas);

        List<Map<String, Object>> ventasSemana = ventaService.ventasPorSemana(LocalDateTime.now().minusWeeks(6)).stream()
                .map(row -> Map.<String, Object>of(
                        "semana", row[0],
                        "total", row[1]))
                .collect(Collectors.toList());
        model.addAttribute("ventasSemana", ventasSemana);
        return "dashboard/index";
    }
}
