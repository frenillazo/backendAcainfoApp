package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Horario;
import com.acainfo.backendAcainfoApp.repository.HorarioRepository;
import com.acainfo.backendAcainfoApp.service.HorarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository repository;

    public HorarioServiceImpl(HorarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Horario> findAll() {
        return repository.findAll();
    }

    @Override
    public Horario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario not found with id " + id));
    }

    @Override
    public Horario create(Horario horario) {
        return repository.save(horario);
    }

    @Override
    public Horario update(Long id, Horario horario) {
        Horario existing = findById(id);
        existing.setAsignatura(horario.getAsignatura());
        existing.setProfesor(horario.getProfesor());
        existing.setFecha(horario.getFecha());
        existing.setHoraInicio(horario.getHoraInicio());
        existing.setHoraFin(horario.getHoraFin());
        existing.setActivo(horario.getActivo());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Horario existing = findById(id);
        repository.delete(existing);
    }

    @Override
    public List<Horario> findByAsignaturaId(Long asignaturaId) {
        return repository.findByAsignaturaId(asignaturaId);
    }

    @Override
    public List<Horario> findByProfesorId(Long profesorId) {
        return repository.findByProfesorId(profesorId);
    }

    @Override
    public List<Horario> findByFecha(LocalDate fecha) {
        return repository.findByFecha(fecha);
    }
}
