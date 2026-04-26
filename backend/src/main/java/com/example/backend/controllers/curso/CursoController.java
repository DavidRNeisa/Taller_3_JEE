package com.example.backend.controllers.curso;

import com.example.backend.entities.Curso;
import com.example.backend.entities.Inscripcion;
import com.example.backend.repositories.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final InscripcionRepository inscripcionRepository;

    @GetMapping("/inscrito/{alumnoId}")
    public Curso obtenerCursoInscrito(@PathVariable Long alumnoId) {

        Inscripcion inscripcion = inscripcionRepository.findByAlumnoId(alumnoId)
                .orElseThrow(() -> new RuntimeException("No inscrito"));

        return inscripcion.getCurso();
    }
}