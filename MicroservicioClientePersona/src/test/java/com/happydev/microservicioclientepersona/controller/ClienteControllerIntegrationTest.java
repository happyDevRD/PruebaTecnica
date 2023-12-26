//package com.happydev.microservicioclientepersona.controller;
//
//import com.happydev.microservicioclientepersona.controllers.ClienteController;
//import com.happydev.microservicioclientepersona.entity.Cliente;
//import com.happydev.microservicioclientepersona.service.ClienteService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.mockito.Mockito.*;
//
//@WebMvcTest(ClienteController.class)
//class ClienteControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ClienteService clienteService;
//
//    private Cliente cliente;
//
//    @BeforeEach
//    void setUp() {
//        cliente = new Cliente();
//        cliente.setId(1L);
//        cliente.setNombre("John Doe");
//        cliente.setEstado(true);
//        cliente.setContraseña("Passw0rd!");
//        cliente.setIdentificacion("12345678");
//        cliente.setDireccion("123 Main St");
//        cliente.setTelefono("1234567890");
//        cliente.setGenero("Masculino");
//        cliente.setEdad(30);
//    }
//
//    @Test
//    void testCrearCliente() throws Exception {
//        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);
//
//        mockMvc.perform(post("/clientes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{" +
//                                "\"nombre\": \"John Doe\"," +
//                                "\"estado\": true," +
//                                "\"contraseña\": \"Passw0rd!\"," +
//                                "\"identificacion\": \"12345678\"," +
//                                "\"direccion\": \"123 Main St\"," +
//                                "\"telefono\": \"1234567890\"," +
//                                "\"genero\": \"Masculino\"," +
//                                "\"edad\": 30" +
//                                "}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nombre").value("John Doe"));
//
//        verify(clienteService, times(1)).save(any(Cliente.class));
//    }
//
//}
