package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "alumnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private Integer claseActual;

    // Relaciones
    @OneToMany(mappedBy = "alumno")
    private List<Entrega> entregas;

    @OneToMany(mappedBy = "alumno")
    private List<Calificacion> calificaciones;

    @OneToMany(mappedBy = "alumno")
    private List<Recomendacion> recomendaciones;
}