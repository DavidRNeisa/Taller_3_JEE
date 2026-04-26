package com.example.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaseResponse {

    private Long id;
    private Integer numero;
    private String titulo;
    private String descripcion;
}