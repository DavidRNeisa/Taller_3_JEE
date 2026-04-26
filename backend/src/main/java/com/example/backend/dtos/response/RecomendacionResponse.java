package com.example.backend.dtos.response;

import com.example.backend.enums.TipoRecomendacion;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecomendacionResponse {

    private Long id;
    private TipoRecomendacion tipo;
    private String mensaje;
    private LocalDateTime fechaGeneracion;
}