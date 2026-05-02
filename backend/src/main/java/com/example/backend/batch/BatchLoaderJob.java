package com.example.backend.batch;

import com.example.backend.entities.*;
import com.example.backend.enums.TipoContenido;
import com.example.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BatchLoaderJob {

    private static final int TOTAL_CLASES = 36;

    private final CursoRepository cursoRepository;
    private final ClaseRepository claseRepository;
    private final ContenidoRepository contenidoRepository;
    private final TareaRepository tareaRepository;
    private final AlumnoRepository alumnoRepository;
    private final InscripcionRepository inscripcionRepository;

    @Value("${app.content.location:../contenido}")
    private String contentLocation;

    @Value("${app.content.base-url:http://localhost:8080/contenido}")
    private String contentBaseUrl;

    private static final List<String> TITULOS = List.of(
            "Introducción a las Estructuras de Datos",
            "Complejidad Algorítmica y Notación Big-O",
            "Tipos Abstractos de Datos",
            "Taller de Complejidad Algorítmica",
            "Templates y Programación Genérica en C++",
            "La STL de C++",
            "Multilistas y Composición de Estructuras",
            "Taller de Estructuras Lineales",
            "Ejercicios Tipo Parcial — Estructuras Lineales",
            "Laboratorio Guiado — Listas Enlazadas",
            "Recursión",
            "Proyecto — Entrega 1",
            "Introducción a Árboles",
            "Árboles Binarios de Búsqueda",
            "Árboles Balanceados",
            "Árboles de Partición del Espacio",
            "Montículos",
            "Taller de Árboles",
            "Árboles de Expresión",
            "Árboles de Codificación — Huffman",
            "Ejercicios Tipo Parcial — Árboles",
            "Laboratorio Guiado — Implementación BST Completa",
            "Tablas de Dispersión",
            "Proyecto — Entrega 2",
            "Introducción a Grafos",
            "Recorridos en Grafos — DFS y BFS",
            "Caminos de Euler y Hamilton",
            "Prim y Dijkstra",
            "Kruskal y Floyd-Warshall",
            "Taller de Grafos",
            "Matriz de Caminos y Grafos Bipartitos",
            "Cierre del Curso",
            "Preparación del Proyecto Final",
            "Parcial 3 — Guía de Preparación",
            "Ajustes Finales del Proyecto",
            "Entrega Final del Proyecto"
    );

    public void ejecutar() {
        cargarCursoYClases();
        cargarContenidos();
        cargarTareas();
        cargarAlumnoDemo();
    }

    public void cargarCursoYClases() {

        Curso curso = cursoRepository.findAll().stream()
                .filter(c -> "Estructuras de Datos".equalsIgnoreCase(c.getTitulo()))
                .findFirst()
                .orElseGet(() -> cursoRepository.save(Curso.builder()
                        .titulo("Estructuras de Datos")
                        .descripcion("Curso completo de estructuras de datos con 36 clases, recursos, tareas y recomendaciones.")
                        .totalClases(TOTAL_CLASES)
                        .build()));

        curso.setTotalClases(TOTAL_CLASES);
        cursoRepository.save(curso);

        Map<Integer, Clase> clasesExistentes = claseRepository.findByCursoId(curso.getId())
                .stream()
                .filter(c -> c.getNumero() != null)
                .collect(Collectors.toMap(
                        Clase::getNumero,
                        c -> c,
                        (a, b) -> a
                ));

        for (int i = 1; i <= TOTAL_CLASES; i++) {
            if (!clasesExistentes.containsKey(i)) {
                Clase clase = Clase.builder()
                        .numero(i)
                        .titulo("Clase " + i + ": " + TITULOS.get(i - 1))
                        .descripcion("Contenido correspondiente a la clase " + i + " del curso de Estructuras de Datos.")
                        .curso(curso)
                        .build();

                claseRepository.save(clase);
            }
        }
    }

    public void cargarContenidos() {

        if (contenidoRepository.count() > 0) {
            return;
        }

        List<Clase> clases = claseRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Clase::getNumero))
                .toList();

        for (Clase clase : clases) {
            int numero = clase.getNumero();

            String numeroFormato = String.format("%02d", numero);

            Contenido html = Contenido.builder()
                    .titulo("Contenido HTML - Clase " + numero)
                    .tipo(TipoContenido.HTML)
                    .url(contentBaseUrl + "/html/clase_" + numeroFormato + ".html")
                    .orden(1)
                    .clase(clase)
                    .build();

            contenidoRepository.save(html);

            List<String> imagenes = buscarImagenes(numeroFormato);

            int orden = 2;
            for (String imagen : imagenes) {
                Contenido contenidoImagen = Contenido.builder()
                        .titulo("Imagen de apoyo - Clase " + numero)
                        .tipo(TipoContenido.IMAGEN)
                        .url(contentBaseUrl + "/imagenes/" + imagen)
                        .orden(orden++)
                        .clase(clase)
                        .build();

                contenidoRepository.save(contenidoImagen);
            }
        }
    }

    private List<String> buscarImagenes(String numeroFormato) {
        Path imagenesPath = Paths.get(contentLocation, "imagenes").toAbsolutePath().normalize();

        if (!Files.exists(imagenesPath)) {
            return List.of();
        }

        try (Stream<Path> stream = Files.list(imagenesPath)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(nombre -> nombre.startsWith("clase_" + numeroFormato))
                    .sorted()
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public void cargarTareas() {

        if (tareaRepository.count() > 0) {
            return;
        }

        List<Clase> clases = claseRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Clase::getNumero))
                .toList();

        for (Clase clase : clases) {
            Tarea tarea = Tarea.builder()
                    .titulo("Tarea clase " + clase.getNumero())
                    .descripcion("Actividad práctica de la clase " + clase.getNumero() + ": " + clase.getTitulo())
                    .fechaLimite(LocalDateTime.now().plusDays(5 + clase.getNumero()))
                    .ponderacion(100.0)
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

        Curso curso = cursoRepository.findAll().stream()
                .filter(c -> "Estructuras de Datos".equalsIgnoreCase(c.getTitulo()))
                .findFirst()
                .orElse(null);

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