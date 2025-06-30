package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Horario;

import java.time.LocalDate;
import java.util.List;

/**
 * Operaciones CRUD y consultas sobre horarios.
 */
public interface HorarioService {

    /**
     * Devuelve todos los horarios.
     */
    List<Horario> findAll();

    /**
     * Busca un horario por su ID.
     */
    Horario findById(Long id);

    /**
     * Crea un nuevo horario.
     */
    Horario create(Horario horario);

    /**
     * Actualiza un horario existente.
     */
    Horario update(Long id, Horario horario);

    /**
     * Elimina un horario.
     */
    void delete(Long id);

    /**
     * Lista los horarios de una asignatura.
     */
    List<Horario> findByAsignaturaId(Long asignaturaId);

    /**
     * Lista los horarios de un profesor.
     */
    List<Horario> findByProfesorId(Long profesorId);

    /**
     * Lista los horarios de una fecha concreta.
     */
    List<Horario> findByFecha(LocalDate fecha);
}
