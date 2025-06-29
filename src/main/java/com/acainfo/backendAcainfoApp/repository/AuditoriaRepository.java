package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByTabla(String tabla);
    List<Auditoria> findByUsuario(String usuario);
    List<Auditoria> findByOperacion(String operacion);
}
