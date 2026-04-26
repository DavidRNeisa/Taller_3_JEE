package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JsonIgnoreProperties("clases")
    private Curso curso;

    @JsonIgnore
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    private List<Contenido> contenidos;

    @JsonIgnore
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    private List<Tarea> tareas;
}