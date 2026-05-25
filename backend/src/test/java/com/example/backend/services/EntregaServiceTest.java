package com.example.backend.services;

import java.time.LocalDateTime;
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
import org.springframework.mock.web.MockMultipartFile;

import com.example.backend.entities.Alumno;
import com.example.backend.entities.Calificacion;
import com.example.backend.entities.Clase;
import com.example.backend.entities.Entrega;
import com.example.backend.entities.Tarea;
import com.example.backend.enums.EstadoEntrega;
import com.example.backend.repositories.AlumnoRepository;
import com.example.backend.repositories.CalificacionRepository;
import com.example.backend.repositories.EntregaRepository;
import com.example.backend.repositories.TareaRepository;
import com.example.backend.storage.StorageService;

@ExtendWith(MockitoExtension.class)
class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private RecomendacionService recomendacionService;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private EntregaService entregaService;

    @Test
    void registrarEntregaDebeCrearEntregaATiempoYGenerarNotaAlta() {
        Alumno alumno = Alumno.builder().id(1L).nombre("Ana").email("ana@lms.com").build();
        Clase clase = Clase.builder().id(11L).titulo("Clase 1").build();
        Tarea tarea = Tarea.builder()
                .id(21L)
                .titulo("Actividad")
                .fechaLimite(LocalDateTime.now().plusDays(1))
                .clase(clase)
                .build();

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(tareaRepository.findById(21L)).thenReturn(Optional.of(tarea));
        when(entregaRepository.findByAlumnoIdAndTareaId(1L, 21L)).thenReturn(Optional.empty());
        when(storageService.guardar(any())).thenReturn("/files/entrega.pdf");
        when(entregaRepository.save(any(Entrega.class))).thenAnswer(invocation -> {
            Entrega entrega = invocation.getArgument(0);
            entrega.setId(99L);
            return entrega;
        });
        when(calificacionRepository.save(any(Calificacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MockMultipartFile archivo = new MockMultipartFile(
                "archivo",
                "entrega.pdf",
                "application/pdf",
                "contenido".getBytes()
        );

        Entrega entrega = entregaService.registrarEntrega(1L, 21L, archivo);

        ArgumentCaptor<Calificacion> calificacionCaptor = ArgumentCaptor.forClass(Calificacion.class);
        verify(calificacionRepository).save(calificacionCaptor.capture());
        verify(recomendacionService).generarRecomendacion(1L);

        assertThat(entrega.getId()).isEqualTo(99L);
        assertThat(entrega.getEstadoEntrega()).isEqualTo(EstadoEntrega.A_TIEMPO);
        assertThat(entrega.getArchivoUrl()).isEqualTo("/files/entrega.pdf");
        assertThat(calificacionCaptor.getValue().getNota()).isEqualTo(5.0);
        assertThat(calificacionCaptor.getValue().getClase()).isEqualTo(clase);
    }

    @Test
    void registrarEntregaDebeMarcarTardeYAsignarNotaBase() {
        Alumno alumno = Alumno.builder().id(2L).nombre("Luis").email("luis@lms.com").build();
        Clase clase = Clase.builder().id(12L).titulo("Clase 2").build();
        Tarea tarea = Tarea.builder()
                .id(22L)
                .titulo("Actividad tardia")
                .fechaLimite(LocalDateTime.now().minusDays(1))
                .clase(clase)
                .build();

        when(alumnoRepository.findById(2L)).thenReturn(Optional.of(alumno));
        when(tareaRepository.findById(22L)).thenReturn(Optional.of(tarea));
        when(entregaRepository.findByAlumnoIdAndTareaId(2L, 22L)).thenReturn(Optional.empty());
        when(storageService.guardar(any())).thenReturn("/files/tarde.pdf");
        when(entregaRepository.save(any(Entrega.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(calificacionRepository.save(any(Calificacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MockMultipartFile archivo = new MockMultipartFile(
                "archivo",
                "tarde.pdf",
                "application/pdf",
                "contenido".getBytes()
        );

        Entrega entrega = entregaService.registrarEntrega(2L, 22L, archivo);

        ArgumentCaptor<Calificacion> calificacionCaptor = ArgumentCaptor.forClass(Calificacion.class);
        verify(calificacionRepository).save(calificacionCaptor.capture());

        assertThat(entrega.getEstadoEntrega()).isEqualTo(EstadoEntrega.TARDE);
        assertThat(calificacionCaptor.getValue().getNota()).isEqualTo(3.0);
    }

    @Test
    void registrarEntregaDebeRechazarDuplicados() {
        when(alumnoRepository.findById(3L)).thenReturn(Optional.of(Alumno.builder().id(3L).build()));
        when(tareaRepository.findById(23L)).thenReturn(Optional.of(Tarea.builder().id(23L).fechaLimite(LocalDateTime.now().plusDays(1)).build()));
        when(entregaRepository.findByAlumnoIdAndTareaId(3L, 23L)).thenReturn(Optional.of(Entrega.builder().id(77L).build()));

        MockMultipartFile archivo = new MockMultipartFile(
                "archivo",
                "dup.pdf",
                "application/pdf",
                "contenido".getBytes()
        );

        assertThatThrownBy(() -> entregaService.registrarEntrega(3L, 23L, archivo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe una entrega para esta tarea");
    }
}