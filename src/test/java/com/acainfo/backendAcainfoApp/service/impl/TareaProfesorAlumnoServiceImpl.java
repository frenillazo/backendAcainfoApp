package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumno;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumnoId;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.TareaProfesorAlumnoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareaProfesorAlumnoServiceImplTest {

    @Mock
    private TareaProfesorAlumnoRepository repo;

    @InjectMocks
    private TareaProfesorAlumnoServiceImpl service;

    private TareaProfesorAlumno tpa;
    private TareaProfesorAlumnoId id;
    private TareaProfesor tareaProfesor;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder().id(1L).nombre("A").email("a@e.com").password("x").build();
        tareaProfesor = TareaProfesor.builder().idTarea(1L).build();
        id = new TareaProfesorAlumnoId(1L, 1L);
        tpa = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("PENDING")
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(tpa));
        List<TareaProfesorAlumno> list = service.findAll();
        assertThat(list).containsExactly(tpa);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returns() {
        when(repo.findById(id)).thenReturn(Optional.of(tpa));
        TareaProfesorAlumno found = service.findById(id);
        assertThat(found).isSameAs(tpa);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("TareaProfesorAlumno not found");
    }

    @Test
    void create_savesAndReturns() {
        TareaProfesorAlumno input = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("PENDING")
                .build();
        TareaProfesorAlumno saved = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("PENDING")
                .build();
        when(repo.save(input)).thenReturn(saved);
        TareaProfesorAlumno out = service.create(input);
        assertThat(out.getEstado()).isEqualTo("PENDING");
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(id)).thenReturn(Optional.of(tpa));
        TareaProfesorAlumno update = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("COMPLETED")
                .build();
        when(repo.save(tpa)).thenReturn(tpa);
        TareaProfesorAlumno out = service.update(id, update);
        assertThat(out.getEstado()).isEqualTo("COMPLETED");
        verify(repo).save(tpa);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(id)).thenReturn(Optional.of(tpa));
        service.delete(id);
        verify(repo).delete(tpa);
    }
}
