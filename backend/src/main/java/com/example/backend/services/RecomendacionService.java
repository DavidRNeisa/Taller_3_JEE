package com.example.backend.services;

import com.example.backend.entities.*;
import com.example.backend.enums.TipoRecomendacion;
import com.example.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecomendacionService {

    private final CalificacionRepository calificacionRepository;
    private final EntregaRepository entregaRepository;
    private final ContenidoRepository contenidoRepository;
    private final RecomendacionRepository recomendacionRepository;
    private final AlumnoRepository alumnoRepository;

    public Recomendacion generarRecomendacion(Long alumnoId) {

        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        List<Calificacion> calificaciones = calificacionRepository.findByAlumnoId(alumnoId);
        List<Entrega> entregas = entregaRepository.findByAlumnoId(alumnoId);

        if (calificaciones.isEmpty()) {
            return crearRecomendacion(
                    alumno,
                    null,
                    TipoRecomendacion.SIGUIENTE_CLASE,
                    "Empieza con la primera clase del curso"
            );
        }

        double promedio = calificaciones.stream()
                .mapToDouble(Calificacion::getNota)
                .average()
                .orElse(0);

        boolean buenRendimiento = evaluarNota(promedio);
        boolean puntual = evaluarPuntualidad(entregas);
        int cobertura = evaluarCobertura(alumnoId);

        if (buenRendimiento && puntual) {
            return crearRecomendacion(
                    alumno,
                    null,
                    TipoRecomendacion.SIGUIENTE_CLASE,
                    "Puedes avanzar a la siguiente clase"
            );
        } else {
            return crearRecomendacion(
                    alumno,
                    null,
                    TipoRecomendacion.REFUERZO,
                    "Se recomienda repasar los contenidos anteriores"
            );
        }
    }

    private boolean evaluarNota(double nota) {
        return nota >= 4.0;
    }

    private boolean evaluarPuntualidad(List<Entrega> entregas) {

        long entregasATiempo = entregas.stream()
                .filter(e -> e.getEstadoEntrega().name().equals("A_TIEMPO"))
                .count();

        return entregasATiempo >= (entregas.size() * 0.7);
    }

    private int evaluarCobertura(Long alumnoId) {
        // Puedes mejorar esto después (progreso por clases)
        return 1;
    }

    private Recomendacion crearRecomendacion(
            Alumno alumno,
            Clase clase,
            TipoRecomendacion tipo,
            String mensaje
    ) {

        Recomendacion recomendacion = Recomendacion.builder()
                .alumno(alumno)
                .clase(clase)
                .tipo(tipo)
                .mensaje(mensaje)
                .fechaGeneracion(LocalDateTime.now())
                .build();

        return recomendacionRepository.save(recomendacion);
    }
}