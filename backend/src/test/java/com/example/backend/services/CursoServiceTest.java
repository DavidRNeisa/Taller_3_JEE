package com.example.backend.services;

import com.example.backend.dtos.request.CursoRequest;
import com.example.backend.dtos.response.CursoResponse;
import com.example.backend.entities.Curso;
import com.example.backend.repositories.CursoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void obtenerTodosDebeMapearCursos() {
        Curso curso = Curso.builder()
                .id(1L)
                .titulo("Arquitectura")
                .descripcion("Curso base")
                .totalClases(8)
                .build();

        when(cursoRepository.findAll()).thenReturn(List.of(curso));

        List<CursoResponse> responses = cursoService.obtenerTodos();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getId()).isEqualTo(1L);
        assertThat(responses.get(0).getTitulo()).isEqualTo("Arquitectura");
        assertThat(responses.get(0).getDescripcion()).isEqualTo("Curso base");
        assertThat(responses.get(0).getTotalClases()).isEqualTo(8);
    }

    @Test
    void crearDebeGuardarYRetornarElCursoPersistido() {
        CursoRequest request = CursoRequest.builder()
                .titulo("Base de Datos")
                .descripcion("Curso de prueba")
                .totalClases(12)
                .build();

        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> {
            Curso saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        CursoResponse response = cursoService.crear(request);

        ArgumentCaptor<Curso> cursoCaptor = ArgumentCaptor.forClass(Curso.class);
        verify(cursoRepository).save(cursoCaptor.capture());

        Curso savedCourse = cursoCaptor.getValue();
        assertThat(savedCourse.getTitulo()).isEqualTo("Base de Datos");
        assertThat(savedCourse.getDescripcion()).isEqualTo("Curso de prueba");
        assertThat(savedCourse.getTotalClases()).isEqualTo(12);
        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getTitulo()).isEqualTo("Base de Datos");
    }

    @Test
    void actualizarDebeModificarCursoExistente() {
        Curso existente = Curso.builder()
                .id(7L)
                .titulo("Viejo")
                .descripcion("Antes")
                .totalClases(4)
                .build();

        CursoRequest request = CursoRequest.builder()
                .titulo("Nuevo")
                .descripcion("Despues")
                .totalClases(6)
                .build();

        when(cursoRepository.findById(7L)).thenReturn(Optional.of(existente));
        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CursoResponse response = cursoService.actualizar(7L, request);

        assertThat(response.getId()).isEqualTo(7L);
        assertThat(response.getTitulo()).isEqualTo("Nuevo");
        assertThat(response.getDescripcion()).isEqualTo("Despues");
        assertThat(response.getTotalClases()).isEqualTo(6);
        verify(cursoRepository).save(existente);
    }

    @Test
    void eliminarDebeBorrarCuandoExiste() {
        when(cursoRepository.existsById(3L)).thenReturn(true);

        cursoService.eliminar(3L);

        verify(cursoRepository).deleteById(3L);
    }

    @Test
    void eliminarDebeFallarCuandoNoExiste() {
        when(cursoRepository.existsById(3L)).thenReturn(false);

        assertThatThrownBy(() -> cursoService.eliminar(3L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Curso no encontrado: 3");
    }
}