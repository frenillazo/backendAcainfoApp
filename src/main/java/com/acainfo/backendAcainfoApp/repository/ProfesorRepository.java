package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByUsuarioId(Long usuarioId);
}
