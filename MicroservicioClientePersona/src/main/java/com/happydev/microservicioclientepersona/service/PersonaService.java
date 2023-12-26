package com.happydev.microservicioclientepersona.service;

import com.happydev.microservicioclientepersona.entity.Persona;
import com.happydev.microservicioclientepersona.repository.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Persona> findById(Long id) {
        return personaRepository.findById(id);
    }

    @Transactional
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    @Transactional
    public Persona update(Persona persona) {
        return personaRepository.findById(persona.getId())
                .map(existingPersona -> personaRepository.save(persona))
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + persona.getId()));
    }

    @Transactional
    public void deleteById(Long id) {
        personaRepository.deleteById(id);
    }


}
