package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import com.acainfo.backendAcainfoApp.repository.AsignaturaRepository;
import com.acainfo.backendAcainfoApp.service.AsignaturaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    private final AsignaturaRepository repository;

    public AsignaturaServiceImpl(AsignaturaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Asignatura> findAll() {
        return repository.findAll();
    }

    @Override
    public Asignatura findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException("Asignatura not found with id " + id));
    }

    @Override
    public Asignatura create(Asignatura asignatura) {
        // Podrías validar nombre único si hace falta
        return repository.save(asignatura);
    }

    @Override
    public Asignatura update(Long id, Asignatura asignatura) {
        Asignatura existing = findById(id);
        existing.setNombre(asignatura.getNombre());
        existing.setCarrera(asignatura.getCarrera());
        existing.setCurso(asignatura.getCurso());
        existing.setCuatrimestre(asignatura.getCuatrimestre());
        existing.setDescripcion(asignatura.getDescripcion());
        existing.setActivo(asignatura.getActivo());
        // Las colecciones (inscripciones, materiales, horarios) se gestionan aparte
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Asignatura existing = findById(id);
        repository.delete(existing);
    }
}
