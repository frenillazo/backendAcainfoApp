package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByAsignaturaId(Long asignaturaId);
    List<Horario> findByProfesorId(Long profesorId);
    List<Horario> findByFecha(LocalDate fecha);
}