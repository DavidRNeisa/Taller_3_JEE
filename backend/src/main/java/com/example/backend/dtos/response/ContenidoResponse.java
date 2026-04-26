package com.example.backend.dtos.response;

import com.example.backend.enums.TipoContenido;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContenidoResponse {

    private Long id;
    private String titulo;
    private TipoContenido tipo;
    private String url;
    private Integer orden;
}