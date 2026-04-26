package com.example.backend.repositories;

import com.example.backend.entities.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    List<Contenido> findByClaseIdOrderByOrdenAsc(Long claseId);
}