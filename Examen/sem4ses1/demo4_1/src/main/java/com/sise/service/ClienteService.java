package com.sise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sise.dto.ClienteDTO;
import com.sise.iservice.IClienteService;
import com.sise.mapper.ClienteMapper;
import com.sise.model.Cliente;
import com.sise.repository.ClienteRepository;

@Service
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    @Override
    public boolean deleteById(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ClienteDTO> buscarPorTermino(String termino) {
        List<Cliente> clientes = clienteRepository.buscarPorTermino(termino);
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long contarTotalClientes() {
        return clienteRepository.contarTotalClientes();
    }

    @Override
    public Optional<ClienteDTO> findByCorreo(String correo) {
        return clienteRepository.findByCorreo(correo)
                .map(clienteMapper::toDTO);
    }
}