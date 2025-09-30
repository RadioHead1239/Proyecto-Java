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

import com.sise.dto.ClienteDTO;
import com.sise.iservice.IClienteService;

@Controller
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/cliente")
    public String clientes(Model model) {
        return "views/cliente";
    }

    @GetMapping("/api/clientes")
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        try {
            List<ClienteDTO> clientes = clienteService.findAll();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/clientes/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        try {
            Optional<ClienteDTO> cliente = clienteService.findById(id);
            return cliente.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/clientes")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO savedCliente = clienteService.save(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/clientes/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            Optional<ClienteDTO> existingCliente = clienteService.findById(id);
            if (existingCliente.isPresent()) {
                clienteDTO.setId(id);
                ClienteDTO updatedCliente = clienteService.save(clienteDTO);
                return ResponseEntity.ok(updatedCliente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        try {
            boolean deleted = clienteService.deleteById(id);
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
