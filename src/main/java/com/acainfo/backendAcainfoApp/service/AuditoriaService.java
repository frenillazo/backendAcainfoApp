package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Auditoria;
import java.util.List;

/**
 * Operaciones de auditoría para registrar cambios en entidades.
 */
public interface AuditoriaService {

    /**
     * Recupera todas las entradas de auditoría.
     */
    List<Auditoria> findAll();

    /**
     * Busca una entrada por su identificador.
     */
    Auditoria findById(Long id);

    /**
     * Crea un nuevo registro de auditoría.
     */
    Auditoria create(Auditoria auditoria);

    /**
     * Actualiza un registro existente.
     */
    Auditoria update(Long id, Auditoria auditoria);

    /**
     * Elimina un registro de auditoría.
     */
    void delete(Long id);

    /**
     * Filtra por nombre de tabla.
     */
    List<Auditoria> findByTabla(String tabla);

    /**
     * Filtra por usuario que realizó la operación.
     */
    List<Auditoria> findByUsuario(String usuario);

    /**
     * Filtra por tipo de operación.
     */
    List<Auditoria> findByOperacion(String operacion);
}
