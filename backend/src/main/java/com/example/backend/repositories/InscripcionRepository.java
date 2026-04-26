package com.example.backend.repositories;

import com.example.backend.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    Optional<Inscripcion> findByAlumnoId(Long alumnoId);
}