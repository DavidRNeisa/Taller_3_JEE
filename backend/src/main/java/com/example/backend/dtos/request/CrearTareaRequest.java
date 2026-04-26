package com.example.backend.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrearTareaRequest {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaLimite;
    private Double ponderacion;
    private Long claseId;
}
