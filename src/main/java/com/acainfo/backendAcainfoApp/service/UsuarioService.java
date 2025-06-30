package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Usuario;
import java.util.List;

/**
 * Servicio para gestionar usuarios (alumnos) de la aplicación.
 */
public interface UsuarioService {

    /**
     * Recupera todos los usuarios registrados.
     * @return lista de usuarios
     */
    List<Usuario> findAll();

    /**
     * Busca un usuario por su ID.
     * @param id identificador del usuario
     * @return usuario encontrado
     */
    Usuario findById(Long id);

    /**
     * Crea un nuevo usuario.
     * @param usuario datos del usuario
     * @return usuario creado (con ID)
     */
    Usuario create(Usuario usuario);

    /**
     * Actualiza un usuario existente.
     * @param id identificador del usuario a actualizar
     * @param usuario nuevos datos del usuario
     * @return usuario actualizado
     */
    Usuario update(Long id, Usuario usuario);

    /**
     * Elimina un usuario.
     * @param id identificador del usuario
     */
    void delete(Long id);
}
