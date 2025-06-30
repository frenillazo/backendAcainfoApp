package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import com.acainfo.backendAcainfoApp.repository.TareaProfesorRepository;
import com.acainfo.backendAcainfoApp.service.TareaProfesorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaProfesorServiceImpl implements TareaProfesorService {

    private final TareaProfesorRepository repository;

    public TareaProfesorServiceImpl(TareaProfesorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TareaProfesor> findAll() {
        return repository.findAll();
    }

    @Override
    public TareaProfesor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TareaProfesor not found with id " + id));
    }

    @Override
    public TareaProfesor create(TareaProfesor tarea) {
        return repository.save(tarea);
    }

    @Override
    public TareaProfesor update(Long id, TareaProfesor tarea) {
        TareaProfesor existing = findById(id);
        existing.setProfesor(tarea.getProfesor());
        existing.setTitulo(tarea.getTitulo());
        existing.setDescripcion(tarea.getDescripcion());
        existing.setFechaCreacion(tarea.getFechaCreacion());
        existing.setFechaEjecucion(tarea.getFechaEjecucion());
        existing.setVisibilidad(tarea.getVisibilidad());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        TareaProfesor existing = findById(id);
        repository.delete(existing);
    }

    @Override
    public List<TareaProfesor> findByProfesorId(Long profesorId) {
        return repository.findByProfesorId(profesorId);
    }

    @Override
    public List<TareaProfesor> findByVisibilidad(String visibilidad) {
        return repository.findByVisibilidad(visibilidad);
    }
}
