package com.acainfo.backendAcainfoApp.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TareaProfesorAlumnoId implements Serializable {
    private Long idTarea;
    private Long idUsuario;
}
