package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private String titulo;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    private List<Contenido> contenidos;

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    private List<Tarea> tareas;
}