package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import java.util.List;

/**
 * Operaciones sobre tareas creadas por profesores.
 */
public interface TareaProfesorService {

    /**
     * Recupera todas las tareas de profesor.
     */
    List<TareaProfesor> findAll();

    /**
     * Busca una tarea por su ID.
     */
    TareaProfesor findById(Long id);

    /**
     * Crea una nueva tarea de profesor.
     */
    TareaProfesor create(TareaProfesor tarea);

    /**
     * Actualiza una tarea existente.
     */
    TareaProfesor update(Long id, TareaProfesor tarea);

    /**
     * Elimina una tarea.
     */
    void delete(Long id);

    /**
     * Lista las tareas de un profesor concreto.
     */
    List<TareaProfesor> findByProfesorId(Long profesorId);

    /**
     * Lista las tareas según su visibilidad.
     */
    List<TareaProfesor> findByVisibilidad(String visibilidad);
}