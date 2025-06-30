package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumno;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumnoId;
import java.util.List;

/**
 * Operaciones sobre la asignación de tareas a alumnos.
 */
public interface TareaProfesorAlumnoService {

    /**
     * Recupera todas las asignaciones de tareas.
     */
    List<TareaProfesorAlumno> findAll();

    /**
     * Busca una asignación por su ID compuesto.
     */
    TareaProfesorAlumno findById(TareaProfesorAlumnoId id);

    /**
     * Crea una nueva asignación.
     */
    TareaProfesorAlumno create(TareaProfesorAlumno tpa);

    /**
     * Actualiza una asignación existente.
     */
    TareaProfesorAlumno update(TareaProfesorAlumnoId id, TareaProfesorAlumno tpa);

    /**
     * Elimina una asignación.
     */
    void delete(TareaProfesorAlumnoId id);

    /**
     * Lista asignaciones por alumno.
     */
    List<TareaProfesorAlumno> findByIdUsuario(Long idUsuario);

    /**
     * Lista asignaciones por tarea.
     */
    List<TareaProfesorAlumno> findByIdTarea(Long idTarea);

    /**
     * Lista asignaciones por estado.
     */
    List<TareaProfesorAlumno> findByEstado(String estado);
}
