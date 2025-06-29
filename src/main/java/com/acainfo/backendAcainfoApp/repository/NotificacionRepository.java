package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioIdNotificacion(Long usuarioId);
    List<Notificacion> findByTipo(String tipo);
    List<Notificacion> findByLeida(Boolean leida);
}
