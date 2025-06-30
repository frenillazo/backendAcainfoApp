package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Notificacion;
import java.util.List;

/**
 * Servicio para gestionar notificaciones enviadas a los usuarios.
 */
public interface NotificacionService {

    /**
     * Recupera todas las notificaciones.
     */
    List<Notificacion> findAll();

    /**
     * Busca una notificacion por su ID.
     */
    Notificacion findById(Long id);

    /**
     * Crea una nueva notificacion.
     */
    Notificacion create(Notificacion notificacion);

    /**
     * Actualiza una notificacion existente.
     */
    Notificacion update(Long id, Notificacion notificacion);

    /**
     * Elimina una notificacion.
     */
    void delete(Long id);

    /**
     * Lista notificaciones de un usuario.
     */
    List<Notificacion> findByUsuarioId(Long usuarioId);

    /**
     * Lista notificaciones por tipo.
     */
    List<Notificacion> findByTipo(String tipo);

    /**
     * Lista notificaciones segun si fueron leidas.
     */
    List<Notificacion> findByLeida(Boolean leida);
}
