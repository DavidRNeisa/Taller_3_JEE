package com.example.backend.repositories;

import com.example.backend.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByClaseId(Long claseId);
}