package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "profesor")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class Profesor implements Serializable {

    @Id
    private Long id;   // coincide con usuario.id

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private Usuario usuario;

    private String especialidad;

    @Builder.Default
    private Boolean activo = Boolean.TRUE;
}
