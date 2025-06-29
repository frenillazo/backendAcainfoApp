package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "asignatura")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String carrera;
    private Integer curso;
    private Integer cuatrimestre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    private Boolean activo = Boolean.TRUE;

    @Builder.Default
    @OneToMany(
        mappedBy   = "asignatura",
        cascade    = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Inscripcion> inscripciones = new HashSet<>();
    @Builder.Default
    @OneToMany(
        mappedBy   = "asignatura",
        cascade    = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Material> materiales = new HashSet<>();
    @Builder.Default
    @OneToMany(
        mappedBy   = "asignatura",
        cascade    = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Horario> horarios = new HashSet<>();
}
