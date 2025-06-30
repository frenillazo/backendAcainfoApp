package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import com.acainfo.backendAcainfoApp.domain.Horario;
import com.acainfo.backendAcainfoApp.domain.Profesor;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.HorarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioServiceImplTest {

    @Mock
    private HorarioRepository repo;

    @InjectMocks
    private HorarioServiceImpl service;

    private Horario horario;
    private Asignatura asignatura;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        asignatura = Asignatura.builder().id(1L).nombre("Mat").build();
        Usuario usuario = Usuario.builder().id(2L).nombre("Profe").email("p@e.com").password("x").build();
        profesor = Profesor.builder().id(2L).usuario(usuario).especialidad("mat").build();
        horario = Horario.builder()
                .id(1L)
                .asignatura(asignatura)
                .profesor(profesor)
                .fecha(LocalDate.of(2025,1,1))
                .horaInicio(LocalTime.of(8,0))
                .horaFin(LocalTime.of(10,0))
                .activo(true)
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(horario));

        List<Horario> list = service.findAll();

        assertThat(list).containsExactly(horario);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsHorario() {
        when(repo.findById(1L)).thenReturn(Optional.of(horario));

        Horario found = service.findById(1L);

        assertThat(found).isSameAs(horario);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Horario not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        Horario input = Horario.builder()
                .asignatura(asignatura)
                .profesor(profesor)
                .fecha(LocalDate.of(2025,2,1))
                .horaInicio(LocalTime.of(9,0))
                .horaFin(LocalTime.of(11,0))
                .activo(true)
                .build();
        Horario saved = Horario.builder()
                .id(3L)
                .asignatura(asignatura)
                .profesor(profesor)
                .fecha(input.getFecha())
                .horaInicio(input.getHoraInicio())
                .horaFin(input.getHoraFin())
                .activo(true)
                .build();
        when(repo.save(input)).thenReturn(saved);

        Horario result = service.create(input);

        assertThat(result.getId()).isEqualTo(3L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(horario));
        Horario update = Horario.builder()
                .asignatura(asignatura)
                .profesor(profesor)
                .fecha(LocalDate.of(2025,3,1))
                .horaInicio(LocalTime.of(10,0))
                .horaFin(LocalTime.of(12,0))
                .activo(false)
                .build();
        when(repo.save(horario)).thenReturn(horario);

        Horario out = service.update(1L, update);

        assertThat(out.getFecha()).isEqualTo(LocalDate.of(2025,3,1));
        assertThat(out.getActivo()).isFalse();
        verify(repo).save(horario);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(horario));

        service.delete(1L);

        verify(repo).delete(horario);
    }
}
