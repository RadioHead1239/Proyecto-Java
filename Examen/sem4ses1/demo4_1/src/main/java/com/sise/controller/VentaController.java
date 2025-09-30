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

import com.sise.dto.VentaDTO;
import com.sise.iservice.IVentaService;

@Controller
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/venta")
    public String ventas(Model model) {
        return "views/venta";
    }

    @GetMapping("/api/ventas")
    public ResponseEntity<List<VentaDTO>> getAllVentas() {
        try {
            List<VentaDTO> ventas = ventaService.findAll();
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/ventas/{id}")
    public ResponseEntity<VentaDTO> getVentaById(@PathVariable Long id) {
        try {
            Optional<VentaDTO> venta = ventaService.findById(id);
            return venta.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/ventas")
    public ResponseEntity<VentaDTO> createVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO savedVenta = ventaService.save(ventaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/ventas/{id}")
    public ResponseEntity<VentaDTO> updateVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        try {
            Optional<VentaDTO> existingVenta = ventaService.findById(id);
            if (existingVenta.isPresent()) {
                ventaDTO.setId(id);
                VentaDTO updatedVenta = ventaService.save(ventaDTO);
                return ResponseEntity.ok(updatedVenta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/ventas/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        try {
            boolean deleted = ventaService.deleteById(id);
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