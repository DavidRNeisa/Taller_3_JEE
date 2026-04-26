package com.example.backend.controllers.contenido;

import com.example.backend.entities.Contenido;
import com.example.backend.repositories.ContenidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@RequiredArgsConstructor
public class ContenidoController {

    private final ContenidoRepository contenidoRepository;

    @GetMapping("/clase/{claseId}")
    public List<Contenido> obtenerContenido(@PathVariable Long claseId) {
        return contenidoRepository.findByClaseIdOrderByOrdenAsc(claseId);
    }
}