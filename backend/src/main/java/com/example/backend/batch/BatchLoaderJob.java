package com.example.backend.batch;

import com.example.backend.entities.*;
import com.example.backend.enums.TipoContenido;
import com.example.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchLoaderJob {

    private final CursoRepository cursoRepository;
    private final ClaseRepository claseRepository;
    private final ContenidoRepository contenidoRepository;
    private final TareaRepository tareaRepository;
    private final AlumnoRepository alumnoRepository;
    private final InscripcionRepository inscripcionRepository;

    public void ejecutar() {
        cargarCursoYClases();
        cargarContenidos();
        cargarTareas();
        cargarAlumnoDemo();
    }

    public void cargarCursoYClases() {

        Curso curso = cursoRepository.findAll().stream().findFirst().orElseGet(() ->
                cursoRepository.save(Curso.builder()
                        .titulo("Estructuras de Datos")
                        .descripcion("Curso completo de estructuras de datos")
                        .totalClases(3)
                        .build())
        );

        if (!claseRepository.findByCursoId(curso.getId()).isEmpty()) {
            return;
        }

        Clase clase1 = Clase.builder()
                .numero(1)
                .titulo("Introducción")
                .descripcion("Conceptos básicos")
                .curso(curso)
                .build();

        Clase clase2 = Clase.builder()
                .numero(2)
                .titulo("Listas")
                .descripcion("Listas enlazadas")
                .curso(curso)
                .build();

        claseRepository.saveAll(List.of(clase1, clase2));
    }

    public void cargarContenidos() {

        List<Clase> clases = claseRepository.findAll();

        if (clases.isEmpty()) return;

        for (Clase clase : clases) {

            Contenido contenido = Contenido.builder()
                    .titulo("Contenido " + clase.getNumero())
                    .tipo(TipoContenido.HTML)
                    .url("http://contenido.com/clase" + clase.getNumero())
                    .orden(1)
                    .clase(clase)
                    .build();

            contenidoRepository.save(contenido);
        }
    }

    public void cargarTareas() {

        if (tareaRepository.count() > 0) {
            return;
        }

        List<Clase> clases = claseRepository.findAll();
        if (clases.isEmpty()) {
            return;
        }

        for (Clase clase : clases) {
            Tarea tarea = Tarea.builder()
                    .titulo("Tarea clase " + clase.getNumero())
                    .descripcion("Entrega practica de la clase " + clase.getNumero())
                    .fechaLimite(LocalDateTime.now().plusDays(5 + clase.getNumero()))
                    .ponderacion(20.0)
                    .clase(clase)
                    .build();

            tareaRepository.save(tarea);
        }
    }

    public void cargarAlumnoDemo() {

        Alumno alumno = alumnoRepository.findByEmail("alumno.demo@lms.local")
                .orElseGet(() -> alumnoRepository.save(Alumno.builder()
                        .nombre("Alumno Demo")
                        .email("alumno.demo@lms.local")
                        .claseActual(1)
                        .build()));

        if (inscripcionRepository.findByAlumnoId(alumno.getId()).isPresent()) {
            return;
        }

        Curso curso = cursoRepository.findAll().stream().findFirst().orElse(null);
        if (curso == null) {
            return;
        }

        inscripcionRepository.save(Inscripcion.builder()
                .alumno(alumno)
                .curso(curso)
                .fechaInscripcion(LocalDateTime.now())
                .build());
    }
}