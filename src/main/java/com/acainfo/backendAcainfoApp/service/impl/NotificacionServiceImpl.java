package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Notificacion;
import com.acainfo.backendAcainfoApp.repository.NotificacionRepository;
import com.acainfo.backendAcainfoApp.service.NotificacionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionServiceImpl(NotificacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Notificacion> findAll() {
        return repository.findAll();
    }

    @Override
    public Notificacion findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion not found with id " + id));
    }

    @Override
    public Notificacion create(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    @Override
    public Notificacion update(Long id, Notificacion notificacion) {
        Notificacion existing = findById(id);
        existing.setUsuario(notificacion.getUsuario());
        existing.setTipo(notificacion.getTipo());
        existing.setTitulo(notificacion.getTitulo());
        existing.setCuerpo(notificacion.getCuerpo());
        existing.setEnviadaEn(notificacion.getEnviadaEn());
        existing.setLeida(notificacion.getLeida());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Notificacion existing = findById(id);
        repository.delete(existing);
    }

    @Override
    public List<Notificacion> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Notificacion> findByTipo(String tipo) {
        return repository.findByTipo(tipo);
    }

    @Override
    public List<Notificacion> findByLeida(Boolean leida) {
        return repository.findByLeida(leida);
    }
}
