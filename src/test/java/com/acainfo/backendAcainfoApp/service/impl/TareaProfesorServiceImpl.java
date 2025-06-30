package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Profesor;
import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.TareaProfesorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareaProfesorServiceImplTest {

    @Mock
    private TareaProfesorRepository repo;

    @InjectMocks
    private TareaProfesorServiceImpl service;

    private TareaProfesor tarea;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Usuario usuario = Usuario.builder().id(2L).nombre("Profe").email("p@e.com").password("x").build();
        profesor = Profesor.builder().id(2L).usuario(usuario).especialidad("mat").build();
        tarea = TareaProfesor.builder()
                .idTarea(1L)
                .profesor(profesor)
                .titulo("T1")
                .descripcion("D1")
                .fechaCreacion(LocalDateTime.of(2025,1,1,10,0))
                .fechaEjecucion(LocalDateTime.of(2025,2,1,10,0))
                .visibilidad("ALL")
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(tarea));
        List<TareaProfesor> list = service.findAll();
        assertThat(list).containsExactly(tarea);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsTarea() {
        when(repo.findById(1L)).thenReturn(Optional.of(tarea));
        TareaProfesor found = service.findById(1L);
        assertThat(found).isSameAs(tarea);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("TareaProfesor not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        TareaProfesor input = TareaProfesor.builder()
                .profesor(profesor)
                .titulo("N")
                .descripcion("D")
                .fechaCreacion(LocalDateTime.now())
                .fechaEjecucion(LocalDateTime.now())
                .visibilidad("ALL")
                .build();
        TareaProfesor saved = TareaProfesor.builder()
                .idTarea(2L)
                .profesor(profesor)
                .titulo("N")
                .descripcion("D")
                .fechaCreacion(input.getFechaCreacion())
                .fechaEjecucion(input.getFechaEjecucion())
                .visibilidad("ALL")
                .build();
        when(repo.save(input)).thenReturn(saved);
        TareaProfesor out = service.create(input);
        assertThat(out.getIdTarea()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(tarea));
        TareaProfesor update = TareaProfesor.builder()
                .profesor(profesor)
                .titulo("Upd")
                .descripcion("UD")
                .fechaCreacion(LocalDateTime.now())
                .fechaEjecucion(LocalDateTime.now().plusDays(1))
                .visibilidad("CUSTOM")
                .build();
        when(repo.save(tarea)).thenReturn(tarea);
        TareaProfesor out = service.update(1L, update);
        assertThat(out.getTitulo()).isEqualTo("Upd");
        assertThat(out.getVisibilidad()).isEqualTo("CUSTOM");
        verify(repo).save(tarea);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(tarea));
        service.delete(1L);
        verify(repo).delete(tarea);
    }
}
