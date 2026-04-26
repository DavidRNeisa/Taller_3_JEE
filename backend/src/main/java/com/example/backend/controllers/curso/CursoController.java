package com.example.backend.controllers.curso;

import com.example.backend.dtos.request.CursoRequest;
import com.example.backend.dtos.response.CursoResponse;
import com.example.backend.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    // 🔹 GET /api/cursos — Listar todos los cursos
    @GetMapping
    public ResponseEntity<List<CursoResponse>> obtenerTodos() {
        return ResponseEntity.ok(cursoService.obtenerTodos());
    }

    // 🔹 GET /api/cursos/{id} — Obtener un curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.obtenerPorId(id));
    }

    // 🔹 POST /api/cursos — Crear un nuevo curso
    @PostMapping
    public ResponseEntity<CursoResponse> crear(@RequestBody CursoRequest request) {
        CursoResponse response = cursoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🔹 PUT /api/cursos/{id} — Actualizar un curso existente
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody CursoRequest request) {
        return ResponseEntity.ok(cursoService.actualizar(id, request));
    }

    // 🔹 DELETE /api/cursos/{id} — Eliminar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}