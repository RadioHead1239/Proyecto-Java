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

import com.sise.dto.ServicioDTO;
import com.sise.iservice.IServicioService;

@Controller
public class ServicioController {

    private final IServicioService servicioService;

    public ServicioController(IServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/servicio")
    public String servicios(Model model) {
        return "views/servicio";
    }

    @GetMapping("/api/servicios")
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        try {
            List<ServicioDTO> servicios = servicioService.findAll();
            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/servicios/{id}")
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Long id) {
        try {
            Optional<ServicioDTO> servicio = servicioService.findById(id);
            return servicio.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/servicios")
    public ResponseEntity<ServicioDTO> createServicio(@RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO savedServicio = servicioService.save(servicioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedServicio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/servicios/{id}")
    public ResponseEntity<ServicioDTO> updateServicio(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO) {
        try {
            Optional<ServicioDTO> existingServicio = servicioService.findById(id);
            if (existingServicio.isPresent()) {
                servicioDTO.setId(id);
                ServicioDTO updatedServicio = servicioService.save(servicioDTO);
                return ResponseEntity.ok(updatedServicio);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/servicios/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        try {
            boolean deleted = servicioService.deleteById(id);
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