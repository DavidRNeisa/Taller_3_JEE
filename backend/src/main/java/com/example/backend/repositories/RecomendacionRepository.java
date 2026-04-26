package com.example.backend.repositories;

import com.example.backend.entities.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {

    List<Recomendacion> findByAlumnoId(Long alumnoId);
}