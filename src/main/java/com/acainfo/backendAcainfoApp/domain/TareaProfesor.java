package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarea_profesor")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TareaProfesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_profesor", nullable = false)
    private Profesor profesor;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaEjecucion;

    @Column(nullable = false, length = 20)
    private String visibilidad;   // "ALL" o "CUSTOM"
}
