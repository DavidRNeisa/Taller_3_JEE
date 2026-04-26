package com.example.backend.entities;

import com.example.backend.enums.TipoContenido;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contenidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoContenido tipo;

    private String url;

    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private Clase clase;
}