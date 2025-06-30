package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.TareaCalendario;
import com.acainfo.backendAcainfoApp.repository.TareaCalendarioRepository;
import com.acainfo.backendAcainfoApp.service.TareaCalendarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TareaCalendarioServiceImpl implements TareaCalendarioService {

    private final TareaCalendarioRepository repository;

    public TareaCalendarioServiceImpl(TareaCalendarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TareaCalendario> findAll() {
        return repository.findAll();
    }

    @Override
    public TareaCalendario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TareaCalendario not found with id " + id));
    }

    @Override
    public TareaCalendario create(TareaCalendario tarea) {
        return repository.save(tarea);
    }

    @Override
    public TareaCalendario update(Long id, TareaCalendario tarea) {
        TareaCalendario existing = findById(id);
        existing.setUsuario(tarea.getUsuario());
        existing.setTitulo(tarea.getTitulo());
        existing.setDescripcion(tarea.getDescripcion());
        existing.setFecha(tarea.getFecha());
        existing.setHoraAviso(tarea.getHoraAviso());
        existing.setCompletada(tarea.getCompletada());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        TareaCalendario existing = findById(id);
        repository.delete(existing);
    }

    @Override
    public List<TareaCalendario> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<TareaCalendario> findByCompletada(Boolean completada) {
        return repository.findByCompletada(completada);
    }

    @Override
    public List<TareaCalendario> findByFechaAfter(LocalDateTime from) {
        return repository.findByFechaAfter(from);
    }
}
