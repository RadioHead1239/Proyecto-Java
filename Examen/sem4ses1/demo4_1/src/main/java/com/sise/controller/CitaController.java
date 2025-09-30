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

import com.sise.dto.CitaDTO;
import com.sise.iservice.ICitaService;

@Controller
public class CitaController {

    private final ICitaService citaService;

    public CitaController(ICitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping("/cita")
    public String citas(Model model) {
        return "views/cita";
    }

    @GetMapping("/api/citas")
    public ResponseEntity<List<CitaDTO>> getAllCitas() {
        try {
            List<CitaDTO> citas = citaService.findAll();
            return ResponseEntity.ok(citas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/citas/{id}")
    public ResponseEntity<CitaDTO> getCitaById(@PathVariable Long id) {
        try {
            Optional<CitaDTO> cita = citaService.findById(id);
            return cita.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/citas")
    public ResponseEntity<CitaDTO> createCita(@RequestBody CitaDTO citaDTO) {
        try {
            CitaDTO savedCita = citaService.save(citaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCita);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/citas/{id}")
    public ResponseEntity<CitaDTO> updateCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) {
        try {
            Optional<CitaDTO> existingCita = citaService.findById(id);
            if (existingCita.isPresent()) {
                citaDTO.setId(id);
                CitaDTO updatedCita = citaService.save(citaDTO);
                return ResponseEntity.ok(updatedCita);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/citas/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        try {
            boolean deleted = citaService.deleteById(id);
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