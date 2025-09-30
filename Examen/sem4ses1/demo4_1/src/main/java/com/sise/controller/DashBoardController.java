package com.sise.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sise.dto.DashboardDTO;
import com.sise.iservice.IDashBoardService;

@Controller
public class DashBoardController {

    private final IDashBoardService dashboardService;

    public DashBoardController(IDashBoardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"/", "/index", "/dashboard"})
    public String index(HttpServletRequest request, Model model) {
        try {
            DashboardDTO dashboard = dashboardService.getDashboardData();
            model.addAttribute("dashboard", dashboard);
        } catch (Exception e) {
            DashboardDTO dashboard = new DashboardDTO();
            dashboard.setTotalClientes(0);
            dashboard.setTotalMascotas(0);
            dashboard.setCitasHoy(0);
            dashboard.setIngresosHoy(java.math.BigDecimal.ZERO);
            model.addAttribute("dashboard", dashboard);
        }
    
        model.addAttribute("currentPath", request.getRequestURI());
    
        return "views/dashboard";
    }
    
   
}
