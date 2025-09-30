package com.sise.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.DashboardDTO;
import com.sise.dto.MascotaDistribucionDTO;
import com.sise.dto.IngresosSemanalesDTO;
import com.sise.iservice.IDashBoardService;
import com.sise.mapper.CitaMapper;
import com.sise.mapper.VentaMapper;
import com.sise.repository.CitaRepository;
import com.sise.repository.ClienteRepository;
import com.sise.repository.MascotaRepository;
import com.sise.repository.PagoRepository;
import com.sise.repository.VentaRepository;

@Service
public class DashBoardService implements IDashBoardService {

    private final ClienteRepository clienteRepo;
    private final MascotaRepository mascotaRepo;
    private final CitaRepository citaRepo;
    private final PagoRepository pagoRepo;
    private final VentaRepository ventaRepo;
    private final CitaMapper citaMapper;
    private final VentaMapper ventaMapper;

    public DashBoardService(
        ClienteRepository clienteRepo,
        MascotaRepository mascotaRepo,
        CitaRepository citaRepo,
        PagoRepository pagoRepo,
        VentaRepository ventaRepo,
        CitaMapper citaMapper,
        VentaMapper ventaMapper
    ) {
        this.clienteRepo = clienteRepo;
        this.mascotaRepo = mascotaRepo;
        this.citaRepo = citaRepo;
        this.pagoRepo = pagoRepo;
        this.ventaRepo = ventaRepo;
        this.citaMapper = citaMapper;
        this.ventaMapper = ventaMapper;
    }

    @Override
    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        dto.setTotalClientes(clienteRepo.count());
        dto.setTotalMascotas(mascotaRepo.count());

        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1).minusNanos(1);

        dto.setCitasHoy(citaRepo.countByFechaCitaBetween(inicio, fin));

        Double ingresosPagos = pagoRepo.sumByFechaPagoBetween(inicio, fin);
        Double ingresosVentas = ventaRepo.sumarVentasPorPeriodo(inicio, fin);
        
        double totalIngresos = 0.0;
        if (ingresosPagos != null) totalIngresos += ingresosPagos;
        if (ingresosVentas != null) totalIngresos += ingresosVentas;
        
        BigDecimal ingresos = BigDecimal.valueOf(totalIngresos);
        dto.setIngresosHoy(ingresos);

        dto.setProximasCitas(
            citaRepo.findTop5ByFechaCitaAfterOrderByFechaCitaAsc(LocalDateTime.now())
                .stream().map(citaMapper::toDTO).collect(Collectors.toList())
        );

        dto.setUltimasVentas(
            ventaRepo.findTop5ByOrderByFechaDesc()
                .stream().map(ventaMapper::toDTO).collect(Collectors.toList())
        );

        dto.setVentasSemanales(
            ventaRepo.ventasPorSemana(LocalDate.now().minusWeeks(8).atStartOfDay())
                .stream().map(ventaMapper::toDTO).collect(Collectors.toList())
        );

        dto.setDistribucionMascotas(
            mascotaRepo.distribucionMascotas()
                .stream()
                .map(result -> {
                    String especie = result[0] != null ? result[0].toString() : "Otros";
                    Number count = (Number) result[1];
                    return new MascotaDistribucionDTO(especie, count.longValue());
                })
                .collect(Collectors.toList())
        );

        LocalDateTime semanaInicio = LocalDateTime.now().minusDays(7);
        dto.setIngresosSemanales(calcularIngresosSemanales(semanaInicio));

        return dto;
    }
    
    private List<IngresosSemanalesDTO> calcularIngresosSemanales(LocalDateTime fechaInicio) {
        List<Object[]> ingresosPagos = pagoRepo.ingresosPorSemana(fechaInicio);
        
        List<Object[]> ingresosVentas = ventaRepo.ingresosPorSemana(fechaInicio);
        
        Map<LocalDate, Double> ingresosPorDia = new HashMap<>();
        
        for (Object[] result : ingresosPagos) {
            LocalDate fecha = result[0] != null ? 
                ((java.sql.Date) result[0]).toLocalDate() : LocalDate.now();
            Double total = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
            ingresosPorDia.put(fecha, ingresosPorDia.getOrDefault(fecha, 0.0) + total);
        }
        
        for (Object[] result : ingresosVentas) {
            LocalDate fecha = result[0] != null ? 
                ((java.sql.Date) result[0]).toLocalDate() : LocalDate.now();
            Double total = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
            ingresosPorDia.put(fecha, ingresosPorDia.getOrDefault(fecha, 0.0) + total);
        }
        
        return ingresosPorDia.entrySet().stream()
            .map(entry -> new IngresosSemanalesDTO(entry.getKey(), entry.getValue()))
            .sorted((a, b) -> a.getFecha().compareTo(b.getFecha()))
            .collect(Collectors.toList());
    }
}
