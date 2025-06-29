package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.TareaCalendario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TareaCalendarioRepository extends JpaRepository<TareaCalendario, Long> {
    List<TareaCalendario> findByUsuarioId(Long usuarioId);
    List<TareaCalendario> findByCompletada(Boolean completada);
    List<TareaCalendario> findByFechaAfter(LocalDateTime from);
}
