package com.sise.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sise.dto.ClienteDTO;
import com.sise.mapper.ClienteMapper;
import com.sise.model.Cliente;
import com.sise.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteApi {

    private final ClienteRepository clienteRepo;

    public ClienteApi(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteRepo.findAll().stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ClienteDTO crear(@RequestBody Cliente cliente) {
        return ClienteMapper.toDTO(clienteRepo.save(cliente));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteRepo.deleteById(id);
    }
}
