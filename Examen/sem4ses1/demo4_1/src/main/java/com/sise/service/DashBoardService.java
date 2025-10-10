package com.sise.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.DashboardDTO;
import com.sise.dto.MascotaDistribucionDTO;
import com.sise.dto.IngresosSemanalesDTO;
import com.sise.dto.VentaDTO;
import com.sise.dto.CitaDTO;
import com.sise.iservice.IDashBoardService;
import com.sise.mapper.CitaMapper;
import com.sise.mapper.VentaMapper;
import com.sise.repository.CitaRepository;
import com.sise.repository.ClienteRepository;
import com.sise.repository.MascotaRepository;
import com.sise.repository.PagoRepository;
import com.sise.repository.VentaRepository;
import com.sise.model.Pago;
import com.sise.model.Venta;

@Service
public class DashBoardService implements IDashBoardService {

    private static final ZoneId ZONE_LIMA = ZoneId.of("America/Lima");
    
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

    private LocalDateTime[] getRangoFechasConZonaHoraria(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59, 999999999);
        ZonedDateTime inicioZoned = inicio.atZone(ZONE_LIMA);
        ZonedDateTime finZoned = fin.atZone(ZONE_LIMA);
        LocalDateTime inicioLocal = inicioZoned.toLocalDateTime();
        LocalDateTime finLocal = finZoned.toLocalDateTime();
        System.out.println("Fecha original: " + fecha);
        System.out.println("Rango con zona horaria Lima: " + inicioLocal + " - " + finLocal);
        return new LocalDateTime[]{inicioLocal, finLocal};
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

    @Override
    public List<VentaDTO> getVentasPorFecha(LocalDate fecha) {
        System.out.println("Fecha recibida para ventas: " + fecha);
        
        LocalDateTime[] rango = getRangoFechasConZonaHoraria(fecha);
        LocalDateTime inicio = rango[0];
        LocalDateTime fin = rango[1];
        
        return ventaRepo.findByFechaBetween(inicio, fin)
            .stream()
            .map(ventaMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasPorFecha(LocalDate fecha) {
        LocalDateTime[] rango = getRangoFechasConZonaHoraria(fecha);
        LocalDateTime inicio = rango[0];
        LocalDateTime fin = rango[1];
        
        return citaRepo.findByFechaCitaBetween(inicio, fin)
            .stream()
            .map(citaMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public DashboardDTO getEstadisticasPorFecha(LocalDate fecha) {
        DashboardDTO dto = new DashboardDTO();
        
        System.out.println("Fecha recibida para estadísticas: " + fecha);
        
        LocalDateTime[] rango = getRangoFechasConZonaHoraria(fecha);
        LocalDateTime inicio = rango[0];
        LocalDateTime fin = rango[1];

        dto.setCitasHoy(citaRepo.countByFechaCitaBetween(inicio, fin));

        Double ingresosPagos = pagoRepo.sumByFechaPagoBetween(inicio, fin);
        Double ingresosVentas = ventaRepo.sumarVentasPorPeriodo(inicio, fin);
        
        List<Pago> pagosDelDia = pagoRepo.findByFechaPagoBetween(inicio, fin);
        List<Venta> ventasDelDia = ventaRepo.findByFechaBetween(inicio, fin);
        
        System.out.println("Pagos encontrados en el día: " + pagosDelDia.size());
        System.out.println("Ventas encontradas en el día: " + ventasDelDia.size());
        
        double totalPagosManual = pagosDelDia.stream()
            .mapToDouble(p -> p.getMonto() != null ? p.getMonto().doubleValue() : 0.0)
            .sum();
        double totalVentasManual = ventasDelDia.stream()
            .mapToDouble(v -> v.getTotal() != null ? v.getTotal().doubleValue() : 0.0)
            .sum();
            
        System.out.println("Total pagos manual: " + totalPagosManual);
        System.out.println("Total ventas manual: " + totalVentasManual);
        
        System.out.println("Ingresos de pagos: " + ingresosPagos);
        System.out.println("Ingresos de ventas: " + ingresosVentas);
        
        double totalIngresos = 0.0;
        
        if (ingresosPagos != null && ingresosPagos > 0) {
            totalIngresos += ingresosPagos;
            System.out.println("Sumando pagos (con filtro): " + ingresosPagos);
        } else {
            totalIngresos += totalPagosManual;
            System.out.println("Sumando pagos (manual): " + totalPagosManual);
        }
        
        if (ingresosVentas != null && ingresosVentas > 0) {
            totalIngresos += ingresosVentas;
            System.out.println("Sumando ventas (con filtro): " + ingresosVentas);
        } else {
            totalIngresos += totalVentasManual;
            System.out.println("Sumando ventas (manual): " + totalVentasManual);
        }
        
        System.out.println("Total ingresos calculado: " + totalIngresos);
        
        BigDecimal ingresos = BigDecimal.valueOf(totalIngresos);
        dto.setIngresosHoy(ingresos);
        
        System.out.println("Ingresos finales en DTO: " + ingresos);

        Long mascotasAtendidas = mascotaRepo.contarMascotasAtendidasPorFecha(inicio, fin);
        dto.setTotalMascotas(mascotasAtendidas != null ? mascotasAtendidas : 0L);

        Long clientesAtendidos = clienteRepo.contarClientesAtendidosPorFecha(inicio, fin);
        dto.setTotalClientes(clientesAtendidos != null ? clientesAtendidos : 0L);
        dto.setProximasCitas(
            citaRepo.findByFechaCitaBetween(inicio, fin)
                .stream().map(citaMapper::toDTO).collect(Collectors.toList())
        );

        dto.setUltimasVentas(
            ventaRepo.findByFechaBetween(inicio, fin)
                .stream().map(ventaMapper::toDTO).collect(Collectors.toList())
        );

        return dto;
    }
}
