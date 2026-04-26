package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private LocalDateTime fechaLimite;

    private Double ponderacion;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private Clase clase;

    public boolean estaVencida() {
        return LocalDateTime.now().isAfter(this.fechaLimite);
    }
}