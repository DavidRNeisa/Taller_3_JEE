package com.example.backend.controllers.recomendacion;

import com.example.backend.entities.Recomendacion;
import com.example.backend.repositories.RecomendacionRepository;
import com.example.backend.services.RecomendacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@RequiredArgsConstructor
public class RecomendacionController {

    private final RecomendacionRepository recomendacionRepository;
    private final RecomendacionService recomendacionService;

    @GetMapping("/{alumnoId}")
    public List<Recomendacion> obtenerRecomendaciones(@PathVariable Long alumnoId) {
        return recomendacionRepository.findByAlumnoId(alumnoId);
    }

    @PostMapping("/{alumnoId}")
    public Recomendacion generar(@PathVariable Long alumnoId) {
        return recomendacionService.generarRecomendacion(alumnoId);
    }
}