package com.sise.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sise.mapper.ClienteMapper;
import com.sise.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteApi {

    private final ClienteRepository clienteRepo;
    private final ClienteMapper clienteMapper;

    public ClienteApi(ClienteRepository clienteRepo, ClienteMapper clienteMapper) {
        this.clienteRepo = clienteRepo;
        this.clienteMapper = clienteMapper;
    }

    /*@GetMapping
    public List<ClienteDTO> listar() {
        return clienteRepo.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }*/

    /*@PostMapping
    public ClienteDTO crear(@RequestBody Cliente cliente) {
        return clienteMapper.toDTO(clienteRepo.save(cliente));
    }*/

   /* @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteRepo.deleteById(id);
    }*/
}
