package com.example.backend.entities;

import com.example.backend.enums.EstadoEntrega;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaEntrega;

    private String archivoUrl;

    @Enumerated(EnumType.STRING)
    private EstadoEntrega estadoEntrega;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "tarea_id")
    private Tarea tarea;

    public EstadoEntrega calcularEstado(LocalDateTime fechaLimite) {
        if (fechaEntrega.isBefore(fechaLimite) || fechaEntrega.isEqual(fechaLimite)) {
            return EstadoEntrega.A_TIEMPO;
        } else {
            return EstadoEntrega.TARDE;
        }
    }
}