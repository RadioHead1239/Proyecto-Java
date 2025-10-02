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

import com.sise.dto.MascotaDTO;
import com.sise.iservice.IMascotaService;

@Controller
public class MascotasController {

    private final IMascotaService mascotaService;

    public MascotasController(IMascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping("/mascota")
    public String mascotas(Model model) {
        return "views/mascota";
    }

    @GetMapping("/api/mascotas")
    public ResponseEntity<List<MascotaDTO>> getAllMascotas() {
        try {
            List<MascotaDTO> mascotas = mascotaService.findAll();
            return ResponseEntity.ok(mascotas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/mascotas/cliente/{clienteId}")
    public ResponseEntity<List<MascotaDTO>> getMascotasByCliente(@PathVariable Long clienteId) {
        try {
            List<MascotaDTO> mascotas = mascotaService.findByClienteId(clienteId);
            return ResponseEntity.ok(mascotas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/mascotas/{id}")
    public ResponseEntity<MascotaDTO> getMascotaById(@PathVariable Long id) {
        try {
            Optional<MascotaDTO> mascota = mascotaService.findById(id);
            return mascota.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/mascotas")
    public ResponseEntity<MascotaDTO> createMascota(@RequestBody MascotaDTO mascotaDTO) {
        try {
            MascotaDTO savedMascota = mascotaService.save(mascotaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMascota);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/mascotas/{id}")
    public ResponseEntity<MascotaDTO> updateMascota(@PathVariable Long id, @RequestBody MascotaDTO mascotaDTO) {
        try {
            Optional<MascotaDTO> existingMascota = mascotaService.findById(id);
            if (existingMascota.isPresent()) {
                mascotaDTO.setId(id);
                MascotaDTO updatedMascota = mascotaService.save(mascotaDTO);
                return ResponseEntity.ok(updatedMascota);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/mascotas/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        try {
            boolean deleted = mascotaService.deleteById(id);
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