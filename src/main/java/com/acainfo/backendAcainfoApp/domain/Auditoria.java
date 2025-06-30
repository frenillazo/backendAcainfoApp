package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuditoria;

    @Column(nullable = false, length = 50)
    private String tabla;

    @Column(nullable = false)
    private Long idRegistro;

    @Column(nullable = false, length = 10)
    private String operacion;        // INSERT / UPDATE / DELETE

    @Column(columnDefinition = "jsonb")
    private String datosAnteriores;

    @Column(columnDefinition = "jsonb")
    private String datosNuevos;

    @Column(length = 100)
    private String usuario;          // quien realizó la acción

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
