package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.TareaCalendario;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Operaciones sobre tareas de calendario de los usuarios.
 */
public interface TareaCalendarioService {

    /**
     * Recupera todas las tareas de calendario.
     */
    List<TareaCalendario> findAll();

    /**
     * Busca una tarea por su ID.
     */
    TareaCalendario findById(Long id);

    /**
     * Crea una nueva tarea de calendario.
     */
    TareaCalendario create(TareaCalendario tarea);

    /**
     * Actualiza una tarea existente.
     */
    TareaCalendario update(Long id, TareaCalendario tarea);

    /**
     * Elimina una tarea.
     */
    void delete(Long id);

    /**
     * Lista las tareas de un usuario concreto.
     */
    List<TareaCalendario> findByUsuarioId(Long usuarioId);

    /**
     * Lista las tareas completadas o no.
     */
    List<TareaCalendario> findByCompletada(Boolean completada);

    /**
     * Lista las tareas con fecha posterior a la indicada.
     */
    List<TareaCalendario> findByFechaAfter(LocalDateTime from);
}
