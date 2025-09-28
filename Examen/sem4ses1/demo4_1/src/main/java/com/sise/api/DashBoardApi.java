package com.sise.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sise.dto.DashboardDTO;
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
}
