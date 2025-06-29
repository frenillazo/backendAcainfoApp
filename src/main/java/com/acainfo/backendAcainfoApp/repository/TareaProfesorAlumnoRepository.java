package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumno;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumnoId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaProfesorAlumnoRepository extends JpaRepository<TareaProfesorAlumno, TareaProfesorAlumnoId> {
    List<TareaProfesorAlumno> findByIdUsuario(Long idUsuario);
    List<TareaProfesorAlumno> findByIdTarea(Long idTarea);
    List<TareaProfesorAlumno> findByEstado(String estado);
}
