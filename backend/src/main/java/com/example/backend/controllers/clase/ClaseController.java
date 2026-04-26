package com.example.backend.controllers.clase;

import com.example.backend.entities.Clase;
import com.example.backend.repositories.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
@RequiredArgsConstructor
public class ClaseController {

    private final ClaseRepository claseRepository;

    @GetMapping("/curso/{cursoId}")
    public List<Clase> obtenerClasesPorCurso(@PathVariable Long cursoId) {
        return claseRepository.findByCursoId(cursoId);
    }
}