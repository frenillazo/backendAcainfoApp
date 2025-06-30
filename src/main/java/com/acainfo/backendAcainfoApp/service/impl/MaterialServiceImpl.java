package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Material;
import com.acainfo.backendAcainfoApp.repository.MaterialRepository;
import com.acainfo.backendAcainfoApp.service.MaterialService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository repository;

    public MaterialServiceImpl(MaterialRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Material> findAll() {
        return repository.findAll();
    }

    @Override
    public Material findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Material not found with id " + id));
    }

    @Override
    public Material create(Material material) {
        return repository.save(material);
    }

    @Override
    public Material update(Long id, Material material) {
        Material existing = findById(id);
        existing.setTitulo(material.getTitulo());
        existing.setUrl(material.getUrl());
        existing.setTipo(material.getTipo());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Material existing = findById(id);
        repository.delete(existing);
    }
}
