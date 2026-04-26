package com.example.backend.controllers.entrega;

import com.example.backend.entities.Entrega;
import com.example.backend.services.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @PostMapping
    public ResponseEntity<Entrega> entregarTarea(
            @RequestParam Long alumnoId,
            @RequestParam Long tareaId,
            @RequestParam MultipartFile archivo
    ) {

        Entrega entrega = entregaService.registrarEntrega(alumnoId, tareaId, archivo);

        return ResponseEntity.ok(entrega);
    }
}