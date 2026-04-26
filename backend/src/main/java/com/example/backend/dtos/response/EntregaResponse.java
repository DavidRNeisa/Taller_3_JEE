package com.example.backend.dtos.response;

import com.example.backend.enums.EstadoEntrega;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EntregaResponse {

    private Long id;
    private Long tareaId;
    private LocalDateTime fechaEntrega;
    private String archivoUrl;
    private EstadoEntrega estado;
}