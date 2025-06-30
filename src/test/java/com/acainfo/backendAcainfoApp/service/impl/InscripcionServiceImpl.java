package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Inscripcion;
import com.acainfo.backendAcainfoApp.repository.InscripcionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InscripcionServiceImplTest {

    @Mock
    private InscripcionRepository repo;

    @InjectMocks
    private InscripcionServiceImpl service;

    private Inscripcion insc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        insc = Inscripcion.builder()
                .id(1L)
                .fechaAlta(LocalDateTime.now())
                .estado("ACTIVE")
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(insc));
        List<Inscripcion> list = service.findAll();
        assertThat(list).containsExactly(insc);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsInscripcion() {
        when(repo.findById(1L)).thenReturn(Optional.of(insc));
        Inscripcion found = service.findById(1L);
        assertThat(found).isSameAs(insc);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Inscripcion not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        Inscripcion input = Inscripcion.builder()
                .fechaAlta(LocalDateTime.now())
                .estado("PENDING")
                .build();
        Inscripcion saved = Inscripcion.builder()
                .id(2L)
                .fechaAlta(input.getFechaAlta())
                .estado("PENDING")
                .build();
        when(repo.save(input)).thenReturn(saved);

        Inscripcion result = service.create(input);
        assertThat(result.getId()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updates() {
        when(repo.findById(1L)).thenReturn(Optional.of(insc));
        Inscripcion update = Inscripcion.builder()
                .fechaAlta(insc.getFechaAlta())
                .fechaBaja(LocalDateTime.now().plusDays(1))
                .estado("CANCELLED")
                .build();
        when(repo.save(insc)).thenReturn(insc);

        Inscripcion out = service.update(1L, update);
        assertThat(out.getEstado()).isEqualTo("CANCELLED");
        assertThat(out.getFechaBaja()).isEqualTo(update.getFechaBaja());
        verify(repo).save(insc);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(insc));
        service.delete(1L);
        verify(repo).delete(insc);
    }
}