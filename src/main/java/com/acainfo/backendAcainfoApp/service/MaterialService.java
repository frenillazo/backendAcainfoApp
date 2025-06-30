package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Material;
import java.util.List;

/**
 * Operaciones CRUD sobre materiales de una asignatura.
 */
public interface MaterialService {

    /**
     * Recupera todos los materiales.
     */
    List<Material> findAll();

    /**
     * Busca un material por su ID.
     */
    Material findById(Long id);

    /**
     * Crea un nuevo material.
     */
    Material create(Material material);

    /**
     * Actualiza un material existente.
     */
    Material update(Long id, Material material);

    /**
     * Elimina un material.
     */
    void delete(Long id);
}
