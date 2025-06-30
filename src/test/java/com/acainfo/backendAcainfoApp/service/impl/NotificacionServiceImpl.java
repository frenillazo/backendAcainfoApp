package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Notificacion;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.NotificacionRepository;
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

class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository repo;

    @InjectMocks
    private NotificacionServiceImpl service;

    private Notificacion not;
    private Usuario user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = Usuario.builder().id(1L).nombre("U").email("u@e.com").password("p").build();
        not = Notificacion.builder()
                .idNotificacion(1L)
                .usuario(user)
                .tipo("EMAIL")
                .titulo("T")
                .cuerpo("C")
                .enviadaEn(LocalDateTime.now())
                .leida(false)
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(not));
        List<Notificacion> list = service.findAll();
        assertThat(list).containsExactly(not);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsNotificacion() {
        when(repo.findById(1L)).thenReturn(Optional.of(not));
        Notificacion found = service.findById(1L);
        assertThat(found).isSameAs(not);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Notificacion not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        Notificacion input = Notificacion.builder()
                .usuario(user)
                .tipo("EMAIL")
                .titulo("A")
                .cuerpo("B")
                .enviadaEn(not.getEnviadaEn())
                .leida(false)
                .build();
        Notificacion saved = Notificacion.builder()
                .idNotificacion(2L)
                .usuario(user)
                .tipo("EMAIL")
                .titulo("A")
                .cuerpo("B")
                .enviadaEn(not.getEnviadaEn())
                .leida(false)
                .build();
        when(repo.save(input)).thenReturn(saved);

        Notificacion result = service.create(input);
        assertThat(result.getIdNotificacion()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(not));
        Notificacion update = Notificacion.builder()
                .usuario(user)
                .tipo("PUSH")
                .titulo("Up")
                .cuerpo("BC")
                .enviadaEn(not.getEnviadaEn())
                .leida(true)
                .build();
        when(repo.save(not)).thenReturn(not);

        Notificacion out = service.update(1L, update);
        assertThat(out.getTipo()).isEqualTo("PUSH");
        assertThat(out.getLeida()).isTrue();
        verify(repo).save(not);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(not));
        service.delete(1L);
        verify(repo).delete(not);
    }
}
