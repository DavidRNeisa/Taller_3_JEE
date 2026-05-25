package com.example.backend.controllers.curso;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.backend.dtos.request.CursoRequest;
import com.example.backend.dtos.response.CursoResponse;
import com.example.backend.services.CursoService;

@ExtendWith(MockitoExtension.class)
class CursoControllerTest {

        @Mock
        private CursoService cursoService;

        @InjectMocks
        private CursoController cursoController;

        @Test
        void obtenerTodosDebeDelegarEnElServicio() {
                when(cursoService.obtenerTodos()).thenReturn(List.of(
                                CursoResponse.builder()
                                                .id(1L)
                                                .titulo("Arquitectura")
                                                .descripcion("Curso base")
                                                .totalClases(8)
                                                .build()
                ));

                ResponseEntity<List<CursoResponse>> response = cursoController.obtenerTodos();

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).hasSize(1);
                assertThat(response.getBody().get(0).getTitulo()).isEqualTo("Arquitectura");
                verify(cursoService).obtenerTodos();
        }

        @Test
        void crearDebeResponderCreatedConLaRespuestaDelServicio() {
                when(cursoService.crear(any(CursoRequest.class))).thenReturn(
                                CursoResponse.builder()
                                                .id(5L)
                                                .titulo("Bases de Datos")
                                                .descripcion("Persistencia y SQL")
                                                .totalClases(10)
                                                .build()
                );

                ResponseEntity<CursoResponse> response = cursoController.crear(
                                CursoRequest.builder()
                                                .titulo("Bases de Datos")
                                                .descripcion("Persistencia y SQL")
                                                .totalClases(10)
                                                .build()
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody().getId()).isEqualTo(5L);
                verify(cursoService).crear(any(CursoRequest.class));
        }

        @Test
        void eliminarDebeDelegarYRetornarNoContent() {
                ResponseEntity<Void> response = cursoController.eliminar(3L);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                verify(cursoService).eliminar(3L);
        }
}