package com.sise.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.PagoDTO;
import com.sise.iservice.IPagoService;
import com.sise.mapper.PagoMapper;
import com.sise.model.Pago;
import com.sise.model.Cita;
import com.sise.repository.PagoRepository;
import com.sise.repository.CitaRepository;

@Service
public class PagoService implements IPagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;
    private final CitaRepository citaRepository;

    public PagoService(PagoRepository pagoRepository, PagoMapper pagoMapper, CitaRepository citaRepository) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
        this.citaRepository = citaRepository;
    }

    @Override
    public List<PagoDTO> findAll() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos.stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PagoDTO> findById(Long id) {
        return pagoRepository.findById(id)
                .map(pagoMapper::toDTO);
    }

    @Override
    public PagoDTO save(PagoDTO pagoDTO) {
        Pago pago = pagoMapper.toEntity(pagoDTO);
        Pago savedPago = pagoRepository.save(pago);
        
        if (savedPago.getEstado() == Pago.Estado.Completado && savedPago.getCita() != null) {
            Optional<Cita> citaOpt = citaRepository.findById(savedPago.getCita().getId());
            if (citaOpt.isPresent()) {
                Cita cita = citaOpt.get();
                if (cita.getEstado() == Cita.Estado.Pendiente || cita.getEstado() == Cita.Estado.Confirmada) {
                    cita.setEstado(Cita.Estado.Completada);
                    citaRepository.save(cita);
                }
            }
        }
        
        return pagoMapper.toDTO(savedPago);
    }

    @Override
    public boolean deleteById(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<PagoDTO> findByCitaId(Long citaId) {
        List<Pago> pagos = pagoRepository.findByCitaId(citaId);
        return pagos.stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoDTO> findByEstado(String estado) {
        List<Pago> pagos = pagoRepository.findByEstado(Pago.Estado.valueOf(estado));
        return pagos.stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoDTO> findByFecha(LocalDateTime fecha) {
        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(fecha, fecha.plusDays(1));
        return pagos.stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Double sumarIngresosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Double total = pagoRepository.sumarIngresosPorPeriodo(fechaInicio, fechaFin);
        return total != null ? total : 0.0;
    }
}
