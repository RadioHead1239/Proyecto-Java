package com.sise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sise.dto.PagoDTO;
import com.sise.iservice.IPagoService;

@Controller
public class PagoController {

    private final IPagoService pagoService;

    public PagoController(IPagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/pago")
    public String pagos(Model model) {
        return "views/pago";
    }

    @GetMapping("/api/pagos")
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        try {
            List<PagoDTO> pagos = pagoService.findAll();
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/pagos/{id}")
    public ResponseEntity<PagoDTO> getPagoById(@PathVariable Long id) {
        try {
            Optional<PagoDTO> pago = pagoService.findById(id);
            return pago.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/pagos")
    public ResponseEntity<PagoDTO> createPago(@RequestBody PagoDTO pagoDTO) {
        try {
            PagoDTO savedPago = pagoService.save(pagoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPago);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/pagos/{id}")
    public ResponseEntity<PagoDTO> updatePago(@PathVariable Long id, @RequestBody PagoDTO pagoDTO) {
        try {
            Optional<PagoDTO> existingPago = pagoService.findById(id);
            if (existingPago.isPresent()) {
                pagoDTO.setId(id);
                PagoDTO updatedPago = pagoService.save(pagoDTO);
                return ResponseEntity.ok(updatedPago);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/pagos/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        try {
            boolean deleted = pagoService.deleteById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}