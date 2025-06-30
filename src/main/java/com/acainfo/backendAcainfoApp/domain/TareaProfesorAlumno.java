package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarea_profesor_alumno")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@IdClass(TareaProfesorAlumnoId.class)
public class TareaProfesorAlumno {

    @Id
    @Column(name = "id_tarea", nullable = false)
    private Long idTarea;

    @Id
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @ManyToOne(optional = false)
    @MapsId("idTarea")
    @JoinColumn(name = "id_tarea", insertable = false, updatable = false)
    private TareaProfesor tareaProfesor;

    @ManyToOne(optional = false)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 20)
    private String estado; // PENDING / COMPLETED / CANCELLED
}
