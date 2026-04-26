package com.example.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlumnoResponse {

    private Long id;
    private String nombre;
    private String email;
    private Integer claseActual;
}
