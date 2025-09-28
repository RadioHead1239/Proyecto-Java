package com.sise.service;

import com.sise.model.Venta;
import com.sise.repository.VentaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;

    @Transactional(readOnly = true)
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta save(Venta venta) {
        if (venta.getDetalles() != null) {
            venta.getDetalles().forEach(detalle -> detalle.setVenta(venta));
        }
        return ventaRepository.save(venta);
    }

    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Venta> ventasRecientes() {
        return ventaRepository.findTop5ByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public List<Object[]> ventasPorSemana(LocalDateTime inicio) {
        return ventaRepository.ventasPorSemana(inicio);
    }
}
