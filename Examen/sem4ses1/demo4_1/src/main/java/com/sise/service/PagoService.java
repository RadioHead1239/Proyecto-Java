package com.sise.service;

import com.sise.model.Pago;
import com.sise.repository.PagoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoService {

    private final PagoRepository pagoRepository;

    @Transactional(readOnly = true)
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pago> findById(Integer id) {
        return pagoRepository.findById(id);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void delete(Integer id) {
        pagoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BigDecimal totalRecaudadoEntre(LocalDateTime inicio, LocalDateTime fin) {
        return pagoRepository.sumByFechaPagoBetween(inicio, fin);
    }
}
