package com.sise.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummary(
        long totalClientes,
        long totalMascotas,
        long totalUsuariosActivos,
        long citasHoy,
        BigDecimal ingresosMes,
        List<String> proximasCitas) {
}
