package com.example.backend.services;

import com.example.backend.dtos.request.CursoRequest;
import com.example.backend.dtos.response.CursoResponse;
import com.example.backend.entities.Curso;
import com.example.backend.repositories.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<CursoResponse> obtenerTodos() {
        return cursoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CursoResponse obtenerPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + id));
        return toResponse(curso);
    }

    public CursoResponse crear(CursoRequest request) {
        Curso curso = Curso.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .totalClases(request.getTotalClases())
                .build();
        return toResponse(cursoRepository.save(curso));
    }

    public CursoResponse actualizar(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + id));
        curso.setTitulo(request.getTitulo());
        curso.setDescripcion(request.getDescripcion());
        curso.setTotalClases(request.getTotalClases());
        return toResponse(cursoRepository.save(curso));
    }

    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado: " + id);
        }
        cursoRepository.deleteById(id);
    }

    private CursoResponse toResponse(Curso curso) {
        return CursoResponse.builder()
                .id(curso.getId())
                .titulo(curso.getTitulo())
                .descripcion(curso.getDescripcion())
                .totalClases(curso.getTotalClases())
                .build();
    }
}