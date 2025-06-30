package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.TareaCalendario;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.TareaCalendarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareaCalendarioServiceImplTest {

    @Mock
    private TareaCalendarioRepository repo;

    @InjectMocks
    private TareaCalendarioServiceImpl service;

    private TareaCalendario tarea;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder().id(1L).nombre("Test").email("t@e.com").password("x").build();
        tarea = TareaCalendario.builder()
                .id(1L)
                .usuario(usuario)
                .titulo("T1")
                .descripcion("D1")
                .fecha(LocalDateTime.of(2025,1,1,10,0))
                .horaAviso(LocalTime.of(9,0))
                .completada(false)
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(tarea));
        List<TareaCalendario> list = service.findAll();
        assertThat(list).containsExactly(tarea);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsTarea() {
        when(repo.findById(1L)).thenReturn(Optional.of(tarea));
        TareaCalendario found = service.findById(1L);
        assertThat(found).isSameAs(tarea);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("TareaCalendario not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        TareaCalendario input = TareaCalendario.builder()
                .usuario(usuario)
                .titulo("N")
                .descripcion("D")
                .fecha(LocalDateTime.now())
                .build();
        TareaCalendario saved = TareaCalendario.builder()
                .id(2L)
                .usuario(usuario)
                .titulo("N")
                .descripcion("D")
                .fecha(input.getFecha())
                .build();
        when(repo.save(input)).thenReturn(saved);
        TareaCalendario out = service.create(input);
        assertThat(out.getId()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(tarea));
        TareaCalendario update = TareaCalendario.builder()
                .usuario(usuario)
                .titulo("Upd")
                .descripcion("UD")
                .fecha(LocalDateTime.of(2025,2,1,10,0))
                .horaAviso(LocalTime.of(8,0))
                .completada(true)
                .build();
        when(repo.save(tarea)).thenReturn(tarea);
        TareaCalendario out = service.update(1L, update);
        assertThat(out.getTitulo()).isEqualTo("Upd");
        assertThat(out.getCompletada()).isTrue();
        verify(repo).save(tarea);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(tarea));
        service.delete(1L);
        verify(repo).delete(tarea);
    }
}
