package com.example.backend.controllers.tarea;

import com.example.backend.entities.Tarea;
import com.example.backend.repositories.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaRepository tareaRepository;

    // 🔹 Obtener tareas por clase
    @GetMapping("/clase/{claseId}")
    public List<Tarea> obtenerTareasPorClase(@PathVariable Long claseId) {
        return tareaRepository.findByClaseId(claseId);
    }

    // 🔹 Obtener una tarea por ID
    @GetMapping("/{id}")
    public Tarea obtenerTarea(@PathVariable Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    // 🔹 Crear tarea (útil para pruebas o panel admin)
    @PostMapping
    public Tarea crearTarea(@RequestBody Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    // 🔹 Eliminar tarea
    @DeleteMapping("/{id}")
    public void eliminarTarea(@PathVariable Long id) {
        tareaRepository.deleteById(id);
    }
}