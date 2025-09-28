package com.sise.service;

import com.sise.model.Usuario;
import com.sise.repository.ClienteRepository;
import com.sise.repository.MascotaRepository;
import com.sise.repository.UsuarioRepository;
import com.sise.web.dto.DashboardSummary;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final ClienteRepository clienteRepository;
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CitaService citaService;
    private final PagoService pagoService;

    public DashboardSummary obtenerResumen() {
        long clientes = clienteRepository.count();
        long mascotas = mascotaRepository.count();
        long usuariosActivos = usuarioRepository.findAll().stream().filter(Usuario::isEnabled).count();
        long citasPendientesHoy = citaService.countPendientesHoy();
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                .withNano(0);
        BigDecimal ingresosMes = pagoService.totalRecaudadoEntre(inicioMes, LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        List<String> proximas = citaService.proximasCitas().stream()
                .map(cita -> String.format("%s - %s (%s)", cita.getFechaCita().format(formatter),
                        cita.getMascota().getNombre(), cita.getServicio().getNombre()))
                .collect(Collectors.toList());
        return new DashboardSummary(clientes, mascotas, usuariosActivos, citasPendientesHoy, ingresosMes, proximas);
    }
}
