package com.sise.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sise.dto.DashboardDTO;
import com.sise.iservice.IDashBoardService;
import com.sise.mapper.CitaMapper;
import com.sise.mapper.MascotaMapper;
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

    public DashBoardService(
        ClienteRepository clienteRepo,
        MascotaRepository mascotaRepo,
        CitaRepository citaRepo,
        PagoRepository pagoRepo,
        VentaRepository ventaRepo
    ) {
        this.clienteRepo = clienteRepo;
        this.mascotaRepo = mascotaRepo;
        this.citaRepo = citaRepo;
        this.pagoRepo = pagoRepo;
        this.ventaRepo = ventaRepo;
    }

    @Override
    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        dto.setTotalClientes(clienteRepo.count());
        dto.setTotalMascotas(mascotaRepo.count());

        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1).minusNanos(1);

        dto.setCitasHoy(citaRepo.countByFechaCitaBetween(inicio, fin));

        BigDecimal ingresos = pagoRepo.sumByFechaPagoBetween(inicio, fin);
        dto.setIngresosHoy(ingresos != null ? ingresos : BigDecimal.ZERO);

        dto.setProximasCitas(
            citaRepo.findTop5ByFechaCitaAfterOrderByFechaCitaAsc(LocalDateTime.now())
                .stream().map(CitaMapper::toDTO).toList()
        );

        dto.setUltimasVentas(
            ventaRepo.findTop5ByOrderByFechaDesc()
                .stream().map(VentaMapper::toDTO).toList()
        );

        dto.setVentasSemanales(
            ventaRepo.ventasPorSemana(LocalDate.now().minusWeeks(8).atStartOfDay())
                .stream().map(VentaMapper::toSemanaDTO).toList()
        );

        dto.setDistribucionMascotas(
            mascotaRepo.distribucionMascotas()
                .stream().map(MascotaMapper::toDistribucionDTO).toList()
        );

        return dto;
    }
}
