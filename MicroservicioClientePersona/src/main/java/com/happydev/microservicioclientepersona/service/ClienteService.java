package com.happydev.microservicioclientepersona.service;

import com.happydev.microservicioclientepersona.entity.Cliente;
import com.happydev.microservicioclientepersona.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(Cliente cliente) {
        return clienteRepository.findById(cliente.getId())
                .map(existingCliente -> clienteRepository.save(cliente))
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + cliente.getId()));
    }

    @Transactional
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}