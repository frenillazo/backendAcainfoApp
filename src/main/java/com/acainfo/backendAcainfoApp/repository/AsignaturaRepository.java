package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByCarrera(String carrera);
    List<Asignatura> findByCurso(Integer curso);
    List<Asignatura> findByCuatrimestre(Integer cuatrimestre);
}
