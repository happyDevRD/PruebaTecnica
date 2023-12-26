package com.happydev.microservicioclientepersona.repository;

import com.happydev.microservicioclientepersona.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}