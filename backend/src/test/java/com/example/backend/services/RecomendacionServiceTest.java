package com.example.backend.services;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.backend.entities.Alumno;
import com.example.backend.entities.Calificacion;
import com.example.backend.entities.Entrega;
import com.example.backend.entities.Recomendacion;
import com.example.backend.enums.EstadoEntrega;
import com.example.backend.enums.TipoRecomendacion;
import com.example.backend.repositories.AlumnoRepository;
import com.example.backend.repositories.CalificacionRepository;
import com.example.backend.repositories.ContenidoRepository;
import com.example.backend.repositories.EntregaRepository;
import com.example.backend.repositories.RecomendacionRepository;

@ExtendWith(MockitoExtension.class)
class RecomendacionServiceTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private ContenidoRepository contenidoRepository;

    @Mock
    private RecomendacionRepository recomendacionRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private RecomendacionService recomendacionService;

    @Test
    void generarRecomendacionDebeSugerirPrimeraClaseCuandoNoHayCalificaciones() {
        Alumno alumno = Alumno.builder().id(1L).nombre("Ana").build();
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(calificacionRepository.findByAlumnoId(1L)).thenReturn(List.of());
        when(entregaRepository.findByAlumnoId(1L)).thenReturn(List.of());
        when(recomendacionRepository.save(any(Recomendacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Recomendacion recomendacion = recomendacionService.generarRecomendacion(1L);

        ArgumentCaptor<Recomendacion> recomendacionCaptor = ArgumentCaptor.forClass(Recomendacion.class);
        verify(recomendacionRepository).save(recomendacionCaptor.capture());

        assertThat(recomendacion.getTipo()).isEqualTo(TipoRecomendacion.SIGUIENTE_CLASE);
        assertThat(recomendacion.getMensaje()).contains("Empieza con la primera clase del curso");
        assertThat(recomendacionCaptor.getValue().getAlumno()).isEqualTo(alumno);
    }

    @Test
    void generarRecomendacionDebeSugerirSiguienteClaseConBuenRendimientoYPuntualidad() {
        Alumno alumno = Alumno.builder().id(2L).nombre("Luis").build();
        List<Calificacion> calificaciones = List.of(
                Calificacion.builder().nota(4.5).build(),
                Calificacion.builder().nota(5.0).build()
        );
        List<Entrega> entregas = List.of(
                Entrega.builder().estadoEntrega(EstadoEntrega.A_TIEMPO).build(),
                Entrega.builder().estadoEntrega(EstadoEntrega.A_TIEMPO).build(),
            Entrega.builder().estadoEntrega(EstadoEntrega.A_TIEMPO).build()
        );

        when(alumnoRepository.findById(2L)).thenReturn(Optional.of(alumno));
        when(calificacionRepository.findByAlumnoId(2L)).thenReturn(calificaciones);
        when(entregaRepository.findByAlumnoId(2L)).thenReturn(entregas);
        when(recomendacionRepository.save(any(Recomendacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Recomendacion recomendacion = recomendacionService.generarRecomendacion(2L);

        assertThat(recomendacion.getTipo()).isEqualTo(TipoRecomendacion.SIGUIENTE_CLASE);
        assertThat(recomendacion.getMensaje()).contains("Puedes avanzar a la siguiente clase");
    }

    @Test
    void generarRecomendacionDebePedirRefuerzoCuandoElRendimientoNoAlcanza() {
        Alumno alumno = Alumno.builder().id(3L).nombre("Marta").build();
        List<Calificacion> calificaciones = List.of(
                Calificacion.builder().nota(3.2).build(),
                Calificacion.builder().nota(3.8).build()
        );
        List<Entrega> entregas = List.of(
                Entrega.builder().estadoEntrega(EstadoEntrega.TARDE).build(),
                Entrega.builder().estadoEntrega(EstadoEntrega.A_TIEMPO).build()
        );

        when(alumnoRepository.findById(3L)).thenReturn(Optional.of(alumno));
        when(calificacionRepository.findByAlumnoId(3L)).thenReturn(calificaciones);
        when(entregaRepository.findByAlumnoId(3L)).thenReturn(entregas);
        when(recomendacionRepository.save(any(Recomendacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Recomendacion recomendacion = recomendacionService.generarRecomendacion(3L);

        assertThat(recomendacion.getTipo()).isEqualTo(TipoRecomendacion.REFUERZO);
        assertThat(recomendacion.getMensaje()).contains("repasar los contenidos anteriores");
    }

    @Test
    void generarRecomendacionDebeFallarSiElAlumnoNoExiste() {
        when(alumnoRepository.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recomendacionService.generarRecomendacion(9L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Alumno no encontrado");
    }
}