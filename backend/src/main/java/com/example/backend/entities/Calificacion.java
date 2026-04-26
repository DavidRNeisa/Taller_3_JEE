package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota;

    private LocalDateTime fechaCalificacion;

    @OneToOne
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private Clase clase;

    public Double calcularNota(Entrega entrega) {
        // lógica simple base (luego la mejoramos en service)
        if (entrega.getEstadoEntrega().name().equals("A_TIEMPO")) {
            return 5.0;
        } else {
            return 3.0;
        }
    }
}