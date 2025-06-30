package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumno;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumnoId;
import com.acainfo.backendAcainfoApp.repository.TareaProfesorAlumnoRepository;
import com.acainfo.backendAcainfoApp.service.TareaProfesorAlumnoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaProfesorAlumnoServiceImpl implements TareaProfesorAlumnoService {

    private final TareaProfesorAlumnoRepository repository;

    public TareaProfesorAlumnoServiceImpl(TareaProfesorAlumnoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TareaProfesorAlumno> findAll() {
        return repository.findAll();
    }

    @Override
    public TareaProfesorAlumno findById(TareaProfesorAlumnoId id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TareaProfesorAlumno not found"));
    }

    @Override
    public TareaProfesorAlumno create(TareaProfesorAlumno tpa) {
        return repository.save(tpa);
    }

    @Override
    public TareaProfesorAlumno update(TareaProfesorAlumnoId id, TareaProfesorAlumno tpa) {
        TareaProfesorAlumno existing = findById(id);
        existing.setTareaProfesor(tpa.getTareaProfesor());
        existing.setUsuario(tpa.getUsuario());
        existing.setEstado(tpa.getEstado());
        return repository.save(existing);
    }

    @Override
    public void delete(TareaProfesorAlumnoId id) {
        TareaProfesorAlumno existing = findById(id);
        repository.delete(existing);
    }

    @Override
    public List<TareaProfesorAlumno> findByIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    @Override
    public List<TareaProfesorAlumno> findByIdTarea(Long idTarea) {
        return repository.findByIdTarea(idTarea);
    }

    @Override
    public List<TareaProfesorAlumno> findByEstado(String estado) {
        return repository.findByEstado(estado);
    }
}
