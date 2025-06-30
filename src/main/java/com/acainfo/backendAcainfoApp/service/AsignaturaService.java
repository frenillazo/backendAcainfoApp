package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import java.util.List;

/**
 * Operaciones CRUD sobre asignaturas.
 */
public interface AsignaturaService {

    /**
     * Devuelve todas las asignaturas.
     */
    List<Asignatura> findAll();

    /**
     * Busca una asignatura por su ID.
     * @param id identificador
     * @return asignatura encontrada
     */
    Asignatura findById(Long id);

    /**
     * Crea una nueva asignatura.
     * @param asignatura datos
     * @return asignatura creada (con ID)
     */
    Asignatura create(Asignatura asignatura);

    /**
     * Actualiza una asignatura existente.
     * @param id identificador
     * @param asignatura nuevos datos
     * @return asignatura actualizada
     */
    Asignatura update(Long id, Asignatura asignatura);

    /**
     * Elimina una asignatura.
     * @param id identificador
     */
    void delete(Long id);
}