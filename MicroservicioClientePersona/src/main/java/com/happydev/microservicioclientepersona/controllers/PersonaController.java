package com.happydev.microservicioclientepersona.controllers;
import com.happydev.microservicioclientepersona.entity.Persona;
import com.happydev.microservicioclientepersona.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/personas")
@CrossOrigin(origins = "*")
public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity.ok(personaService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Persona persona = personaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + id));
        return ResponseEntity.ok(persona);
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@Valid @RequestBody Persona persona) {
        Persona nuevaPersona = personaService.save(persona);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevaPersona.getId())
                .toUri();
        return ResponseEntity.created(location).body(nuevaPersona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @Valid @RequestBody Persona persona) {
        return personaService.findById(id)
                .map(existingPersona -> {
                    persona.setId(id);
                    Persona updatedPersona = personaService.save(persona);
                    return ResponseEntity.ok(updatedPersona);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        Persona persona = personaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + id));
        personaService.deleteById(id);
        return ResponseEntity.noContent().build();  // Cambiado a 204 No Content
    }

}
