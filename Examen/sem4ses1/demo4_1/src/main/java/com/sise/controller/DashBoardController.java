package com.sise.controller;

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
    public String index(Model model) {
        DashboardDTO dashboard = dashboardService.getDashboardData();
        model.addAttribute("dashboard", dashboard); 
        return "views/dashboard";
    }
}
