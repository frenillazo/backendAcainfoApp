package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura asignatura;

    @Column(nullable = false, length = 20)
    private String tipo;          // "video", "pdf", "codigo", "git"

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;           // enlace o ruta en bucket

    @Column(nullable = false)
    private LocalDateTime fechaSubida;

    private LocalDateTime fechaExpiracion; // nullable
}

