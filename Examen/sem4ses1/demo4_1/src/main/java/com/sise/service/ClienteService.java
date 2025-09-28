package com.sise.service;

import com.sise.dto.ClienteDTO;
import com.sise.iservice.IClienteService;
import com.sise.mapper.ClienteMapper;
import com.sise.model.Cliente;
import com.sise.repository.ClienteRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService implements IClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ClienteDTO> listar() {
        return repo.findAll()
                   .stream().map(ClienteMapper::toDTO).toList();
    }

    @Override
    public ClienteDTO buscarPorId(Long id) {
        return repo.findById(id).map(ClienteMapper::toDTO).orElse(null);
    }

    @Override
    public ClienteDTO registrar(ClienteDTO dto) {
        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setFechaRegistro(LocalDate.now());
        return ClienteMapper.toDTO(repo.save(cliente));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        return repo.findById(id).map(c -> {
            c.setNombre(dto.getNombre());
            c.setApellido(dto.getApellido());
            c.setTelefono(dto.getTelefono());
            c.setCorreo(dto.getCorreo());
            return ClienteMapper.toDTO(repo.save(c));
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
