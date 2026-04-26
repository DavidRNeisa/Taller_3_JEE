package com.example.backend.repositories;

import com.example.backend.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    List<Calificacion> findByAlumnoId(Long alumnoId);

    List<Calificacion> findByClaseId(Long claseId);
}