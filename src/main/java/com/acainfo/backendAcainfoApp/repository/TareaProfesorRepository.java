package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaProfesorRepository extends JpaRepository<TareaProfesor, Long> {
    List<TareaProfesor> findByProfesorId(Long profesorId);
    List<TareaProfesor> findByVisibilidad(String visibilidad);
}