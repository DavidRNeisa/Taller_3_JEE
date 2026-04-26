package com.example.backend.batch;

import com.example.backend.entities.*;
import com.example.backend.enums.TipoContenido;
import com.example.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchLoaderJob {

    private final CursoRepository cursoRepository;
    private final ClaseRepository claseRepository;
    private final ContenidoRepository contenidoRepository;

    public void ejecutar() {
        cargarClases();
        cargarContenidos();
    }

    public void cargarClases() {

        if (cursoRepository.count() > 0) return;

        Curso curso = Curso.builder()
                .titulo("Estructuras de Datos")
                .descripcion("Curso completo de estructuras de datos")
                .totalClases(3)
                .build();

        curso = cursoRepository.save(curso);

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
}