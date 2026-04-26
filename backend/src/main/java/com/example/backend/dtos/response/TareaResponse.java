package com.example.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TareaResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaLimite;
    private Double ponderacion;
}