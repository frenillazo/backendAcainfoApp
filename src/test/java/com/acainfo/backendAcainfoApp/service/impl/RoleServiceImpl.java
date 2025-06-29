package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Role;
import com.acainfo.backendAcainfoApp.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository repo;

    @InjectMocks
    private RoleServiceImpl service;

    private Role adminRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminRole = new Role(1L, "ROLE_ADMIN");
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(adminRole));
        List<Role> roles = service.findAll();
        assertThat(roles).containsExactly(adminRole);
        verify(repo).findAll();
    }

    @Test
    void findById_existingId_returnsRole() {
        when(repo.findById(1L)).thenReturn(Optional.of(adminRole));
        Role found = service.findById(1L);
        assertThat(found).isSameAs(adminRole);
    }

    @Test
    void findById_missingId_throwsException() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(9L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Role not found with id 9");
    }

    @Test
    void create_newRole_savesAndReturns() {
        Role newRole = new Role(null, "ROLE_USER");
        when(repo.findByName("ROLE_USER")).thenReturn(Optional.empty());
        when(repo.save(newRole)).thenReturn(new Role(2L, "ROLE_USER"));

        Role saved = service.create(newRole);
        assertThat(saved.getId()).isEqualTo(2L);
        verify(repo).save(newRole);
    }

    @Test
    void create_duplicateName_throwsException() {
        when(repo.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
        assertThatThrownBy(() -> service.create(new Role(null, "ROLE_ADMIN")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Role already exists");
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(adminRole));
        Role toUpdate = new Role(null, "ROLE_SUPER");
        when(repo.save(any())).thenReturn(new Role(1L, "ROLE_SUPER"));

        Role updated = service.update(1L, toUpdate);
        assertThat(updated.getName()).isEqualTo("ROLE_SUPER");
        verify(repo).save(adminRole);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(adminRole));
        service.delete(1L);
        verify(repo).delete(adminRole);
    }
}
