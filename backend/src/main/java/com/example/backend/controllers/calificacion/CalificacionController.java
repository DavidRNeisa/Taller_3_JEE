package com.example.backend.controllers.calificacion;

import com.example.backend.entities.Calificacion;
import com.example.backend.repositories.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionRepository calificacionRepository;

    @GetMapping("/{alumnoId}")
    public List<Calificacion> obtenerCalificaciones(@PathVariable Long alumnoId) {
        return calificacionRepository.findByAlumnoId(alumnoId);
    }
}