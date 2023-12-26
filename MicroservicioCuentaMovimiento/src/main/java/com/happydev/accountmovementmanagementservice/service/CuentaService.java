package com.happydev.accountmovementmanagementservice.service;


import com.happydev.accountmovementmanagementservice.dto.ClienteDTO;
import com.happydev.accountmovementmanagementservice.entity.Cuenta;
import com.happydev.accountmovementmanagementservice.exception.ClienteNotFoundException;
import com.happydev.accountmovementmanagementservice.exception.CustomEntityNotFoundException;
import com.happydev.accountmovementmanagementservice.exception.CustomValidationException;
import com.happydev.accountmovementmanagementservice.exception.ExternalServiceException;
import com.happydev.accountmovementmanagementservice.repository.CuentaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    private final WebClient webClient;

    public CuentaService(CuentaRepository cuentaRepository, WebClient webClient) {
        this.cuentaRepository = cuentaRepository;
        this.webClient = webClient;
    }

    public Mono<ClienteDTO> obtenerCliente(Long clienteId) {
        return webClient.get()
                .uri("/clientes/{id}", clienteId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId)))
                .onStatus(status -> status.is5xxServerError(),
                        response -> Mono.error(new ExternalServiceException("Error en el servicio externo")))
                .bodyToMono(ClienteDTO.class)
                .timeout(Duration.ofSeconds(10));
    }
    public Cuenta crearCuentaParaCliente(Cuenta cuenta, Long clienteId) {
        ClienteDTO clienteDTO = obtenerCliente(clienteId).block();
        if (clienteDTO == null) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId);
        }
        cuenta.setClienteId(clienteDTO.getId());
        return cuentaRepository.save(cuenta);
    }

    public Cuenta crearOActualizarCuenta(Cuenta cuenta) {
        validarCuentaAntesDeGuardar(cuenta);
        return cuentaRepository.save(cuenta);
    }

    private void validarCuentaAntesDeGuardar(Cuenta cuenta) {
        if (cuenta.getNumeroCuenta().trim().isEmpty()) {
            throw new CustomValidationException("El número de cuenta no puede estar vacío.");
        }
    }

    @Transactional(readOnly = true)
    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cuenta obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("Cuenta no encontrada con ID: " + id));
    }

    public Cuenta actualizarCuenta(Cuenta cuenta) {
        return cuentaRepository.findById(cuenta.getId())
                .map(existingCuenta -> cuentaRepository.save(cuenta))
                .orElseThrow(() -> new CustomEntityNotFoundException("Cuenta no encontrada con ID: " + cuenta.getId()));
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.findById(id)
                .ifPresentOrElse(cuentaRepository::delete,
                        () -> { throw new CustomEntityNotFoundException("Cuenta no encontrada con ID: " + id); });
    }

}
