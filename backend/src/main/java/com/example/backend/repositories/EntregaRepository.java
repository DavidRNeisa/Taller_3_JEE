package com.example.backend.repositories;

import com.example.backend.entities.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByAlumnoId(Long alumnoId);

    List<Entrega> findByTareaId(Long tareaId);

    Optional<Entrega> findByAlumnoIdAndTareaId(Long alumnoId, Long tareaId);
}