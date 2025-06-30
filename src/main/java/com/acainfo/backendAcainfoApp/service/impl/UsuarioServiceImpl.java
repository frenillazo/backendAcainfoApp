package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.UsuarioRepository;
import com.acainfo.backendAcainfoApp.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario not found with id " + id));
    }

    @Override
    public Usuario create(Usuario usuario) {
        // Verificar email único
        repository.findByEmail(usuario.getEmail())
            .ifPresent(u -> { throw new IllegalArgumentException("Email already in use: " + usuario.getEmail()); });
        return repository.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        Usuario existing = findById(id);
        existing.setNombre(usuario.getNombre());
        existing.setEmail(usuario.getEmail());
        existing.setCarrera(usuario.getCarrera());
        existing.setCurso(usuario.getCurso());
        existing.setMetodoPago(usuario.getMetodoPago());
        existing.setActivo(usuario.getActivo());
        existing.setFotoUrl(usuario.getFotoUrl());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Usuario existing = findById(id);
        repository.delete(existing);
    }
}
