package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Inscripcion;
import com.acainfo.backendAcainfoApp.repository.InscripcionRepository;
import com.acainfo.backendAcainfoApp.service.InscripcionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository repository;

    public InscripcionServiceImpl(InscripcionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Inscripcion> findAll() {
        return repository.findAll();
    }

    @Override
    public Inscripcion findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Inscripcion not found with id " + id));
    }

    @Override
    public Inscripcion create(Inscripcion inscripcion) {
        // Podrías validar duplicados aquí si conviene
        return repository.save(inscripcion);
    }

    @Override
    public Inscripcion update(Long id, Inscripcion inscripcion) {
        Inscripcion existing = findById(id);
        // Actualiza campos que permitan cambios
        existing.setFechaAlta(inscripcion.getFechaAlta());
        existing.setFechaBaja(inscripcion.getFechaBaja());
        existing.setEstado(inscripcion.getEstado());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Inscripcion existing = findById(id);
        repository.delete(existing);
    }
}
