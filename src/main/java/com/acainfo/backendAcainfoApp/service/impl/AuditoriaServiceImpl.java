package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Auditoria;
import com.acainfo.backendAcainfoApp.repository.AuditoriaRepository;
import com.acainfo.backendAcainfoApp.service.AuditoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository repository;

    public AuditoriaServiceImpl(AuditoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Auditoria> findAll() {
        return repository.findAll();
    }

    @Override
    public Auditoria findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria not found with id " + id));
    }

    @Override
    public Auditoria create(Auditoria auditoria) {
        return repository.save(auditoria);
    }

    @Override
    public Auditoria update(Long id, Auditoria auditoria) {
        Auditoria existing = findById(id);
        existing.setTabla(auditoria.getTabla());
        existing.setIdRegistro(auditoria.getIdRegistro());
        existing.setOperacion(auditoria.getOperacion());
        existing.setDatosAnteriores(auditoria.getDatosAnteriores());
        existing.setDatosNuevos(auditoria.getDatosNuevos());
        existing.setUsuario(auditoria.getUsuario());
        existing.setTimestamp(auditoria.getTimestamp());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Auditoria existing = findById(id);
        repository.delete(existing);
    }

    @Override
    public List<Auditoria> findByTabla(String tabla) {
        return repository.findByTabla(tabla);
    }

    @Override
    public List<Auditoria> findByUsuario(String usuario) {
        return repository.findByUsuario(usuario);
    }

    @Override
    public List<Auditoria> findByOperacion(String operacion) {
        return repository.findByOperacion(operacion);
    }
}