package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Role;
import com.acainfo.backendAcainfoApp.repository.RoleRepository;
import com.acainfo.backendAcainfoApp.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
    }

    @Override
    public Role create(Role role) {
        // Ensure no duplicate name
        repository.findByName(role.getName()).ifPresent(r ->
            { throw new IllegalArgumentException("Role already exists: " + role.getName()); });
        return repository.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        Role existing = findById(id);
        existing.setName(role.getName());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Role existing = findById(id);
        repository.delete(existing);
    }
}
