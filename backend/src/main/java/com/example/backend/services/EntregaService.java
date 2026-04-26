package com.example.backend.services;

import com.example.backend.entities.*;
import com.example.backend.enums.EstadoEntrega;
import com.example.backend.repositories.*;
import com.example.backend.services.RecomendacionService;
import com.example.backend.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final TareaRepository tareaRepository;
    private final AlumnoRepository alumnoRepository;
    private final CalificacionRepository calificacionRepository;
    private final RecomendacionService recomendacionService;
    private final StorageService storageService;

    public Entrega registrarEntrega(Long alumnoId, Long tareaId, MultipartFile archivo) {

        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // Validar si ya entregó
        entregaRepository.findByAlumnoIdAndTareaId(alumnoId, tareaId)
                .ifPresent(e -> {
                    throw new RuntimeException("Ya existe una entrega para esta tarea");
                });

        // Guardar archivo
        String archivoUrl = storageService.guardar(archivo);

        LocalDateTime ahora = LocalDateTime.now();

        // Determinar estado
        EstadoEntrega estado = determinarEstado(ahora, tarea.getFechaLimite());

        // Crear entrega
        Entrega entrega = Entrega.builder()
                .alumno(alumno)
                .tarea(tarea)
                .fechaEntrega(ahora)
                .archivoUrl(archivoUrl)
                .estadoEntrega(estado)
                .build();

        entrega = entregaRepository.save(entrega);

        // Generar nota
        Calificacion calificacion = generarNotaAutomatica(entrega);

        // Generar recomendación
        recomendacionService.generarRecomendacion(alumnoId);

        return entrega;
    }

    private EstadoEntrega determinarEstado(LocalDateTime fechaEntrega, LocalDateTime fechaLimite) {

        if (fechaEntrega.isBefore(fechaLimite) || fechaEntrega.isEqual(fechaLimite)) {
            return EstadoEntrega.A_TIEMPO;
        } else {
            return EstadoEntrega.TARDE;
        }
    }

    private Calificacion generarNotaAutomatica(Entrega entrega) {

        double nota;

        if (entrega.getEstadoEntrega() == EstadoEntrega.A_TIEMPO) {
            nota = 5.0;
        } else {
            nota = 3.0;
        }

        Calificacion calificacion = Calificacion.builder()
                .entrega(entrega)
                .alumno(entrega.getAlumno())
                .clase(entrega.getTarea().getClase())
                .nota(nota)
                .fechaCalificacion(LocalDateTime.now())
                .build();

        return calificacionRepository.save(calificacion);
    }
}