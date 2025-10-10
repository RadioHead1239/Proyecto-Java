package com.sise.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sise.dto.ServicioDTO;
import com.sise.iservice.IServicioService;

@RestController
@RequestMapping("/api/servicios")
public class ServicioApi {

    private final IServicioService servicioService;

    public ServicioApi(IServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        try {
            List<ServicioDTO> servicios = servicioService.findAll();
            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Long id) {
        try {
            Optional<ServicioDTO> servicio = servicioService.findById(id);
            return servicio.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ServicioDTO> createServicio(@RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO savedServicio = servicioService.save(servicioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedServicio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
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
