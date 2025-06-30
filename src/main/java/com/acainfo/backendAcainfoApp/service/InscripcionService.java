package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Inscripcion;
import java.util.List;

/**
 * Servicio para gestionar inscripciones de alumnos.
 */
public interface InscripcionService {

    /**
     * Recupera todas las inscripciones.
     * @return lista de inscripciones
     */
    List<Inscripcion> findAll();

    /**
     * Busca una inscripción por su ID.
     * @param id identificador de la inscripción
     * @return inscripción encontrada
     */
    Inscripcion findById(Long id);

    /**
     * Crea una nueva inscripción.
     * @param inscripcion datos de la inscripción
     * @return inscripción creada (con ID)
     */
    Inscripcion create(Inscripcion inscripcion);

    /**
     * Actualiza una inscripción existente.
     * @param id identificador de la inscripción a actualizar
     * @param inscripcion nuevos datos de la inscripción
     * @return inscripción actualizada
     */
    Inscripcion update(Long id, Inscripcion inscripcion);

    /**
     * Elimina una inscripción.
     * @param id identificador de la inscripción
     */
    void delete(Long id);
}