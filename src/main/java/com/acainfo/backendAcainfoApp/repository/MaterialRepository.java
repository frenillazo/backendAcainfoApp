package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByAsignaturaId(Long asignaturaId);
    List<Material> findByTipo(String tipo);
}