package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Auditoria;
import com.acainfo.backendAcainfoApp.repository.AuditoriaRepository;
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

class AuditoriaServiceImplTest {

    @Mock
    private AuditoriaRepository repo;

    @InjectMocks
    private AuditoriaServiceImpl service;

    private Auditoria aud;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aud = Auditoria.builder()
                .idAuditoria(1L)
                .tabla("usuario")
                .idRegistro(2L)
                .operacion("INSERT")
                .datosAnteriores(null)
                .datosNuevos("{}").build();
        aud.setUsuario("admin");
        aud.setTimestamp(LocalDateTime.now());
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(aud));
        List<Auditoria> list = service.findAll();
        assertThat(list).containsExactly(aud);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsAuditoria() {
        when(repo.findById(1L)).thenReturn(Optional.of(aud));
        Auditoria found = service.findById(1L);
        assertThat(found).isSameAs(aud);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Auditoria not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        Auditoria input = Auditoria.builder()
                .tabla("usuario")
                .idRegistro(3L)
                .operacion("INSERT")
                .datosNuevos("{}").build();
        input.setUsuario("admin");
        input.setTimestamp(aud.getTimestamp());
        Auditoria saved = Auditoria.builder()
                .idAuditoria(2L)
                .tabla("usuario")
                .idRegistro(3L)
                .operacion("INSERT")
                .datosNuevos("{}").build();
        saved.setUsuario("admin");
        saved.setTimestamp(aud.getTimestamp());
        when(repo.save(input)).thenReturn(saved);

        Auditoria result = service.create(input);
        assertThat(result.getIdAuditoria()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(aud));
        Auditoria update = Auditoria.builder()
                .tabla("usuario")
                .idRegistro(2L)
                .operacion("UPDATE")
                .datosNuevos("{\"n\":1}").build();
        update.setUsuario("admin");
        update.setTimestamp(aud.getTimestamp());
        when(repo.save(aud)).thenReturn(aud);

        Auditoria out = service.update(1L, update);
        assertThat(out.getOperacion()).isEqualTo("UPDATE");
        verify(repo).save(aud);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(aud));
        service.delete(1L);
        verify(repo).delete(aud);
    }
}
