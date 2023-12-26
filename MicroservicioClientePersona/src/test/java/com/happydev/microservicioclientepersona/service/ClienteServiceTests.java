package com.happydev.microservicioclientepersona.service;

import com.happydev.microservicioclientepersona.entity.Cliente;
import com.happydev.microservicioclientepersona.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");
        cliente.setEstado(true);
        cliente.setContraseña("Passw0rd!");
        cliente.setIdentificacion("12345678");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("1234567890");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
    }

    @Test
    void testGuardarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.save(cliente);
        assertNotNull(resultado);
        assertEquals(cliente.getNombre(), resultado.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testBuscarClientePorId() {
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.findById(cliente.getId());
        assertTrue(resultado.isPresent());
        assertEquals(cliente.getNombre(), resultado.get().getNombre());
        verify(clienteRepository, times(1)).findById(cliente.getId());
    }

}
