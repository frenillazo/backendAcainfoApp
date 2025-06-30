package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import com.acainfo.backendAcainfoApp.repository.AsignaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsignaturaServiceImplTest {

    @Mock
    private AsignaturaRepository repo;

    @InjectMocks
    private AsignaturaServiceImpl service;

    private Asignatura asig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        asig = Asignatura.builder()
                .id(1L)
                .nombre("Matemáticas")
                .carrera("CS")
                .curso(1)
                .cuatrimestre(1)
                .descripcion("Álgebra y cálculo")
                .activo(true)
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(asig));
        List<Asignatura> list = service.findAll();
        assertThat(list).containsExactly(asig);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsAsignatura() {
        when(repo.findById(1L)).thenReturn(Optional.of(asig));
        Asignatura found = service.findById(1L);
        assertThat(found).isSameAs(asig);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Asignatura not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        Asignatura input = Asignatura.builder()
                .nombre("Física")
                .carrera("CS")
                .curso(1)
                .cuatrimestre(2)
                .descripcion("Mecánica")
                .activo(true)
                .build();
        Asignatura saved = Asignatura.builder()
                .id(2L)
                .nombre("Física")
                .carrera("CS")
                .curso(1)
                .cuatrimestre(2)
                .descripcion("Mecánica")
                .activo(true)
                .build();
        when(repo.save(input)).thenReturn(saved);

        Asignatura result = service.create(input);
        assertThat(result.getId()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(asig));
        Asignatura update = Asignatura.builder()
                .nombre("Matemáticas Avanzadas")
                .carrera("CS")
                .curso(2)
                .cuatrimestre(2)
                .descripcion("Álgebra avanzada")
                .activo(false)
                .build();
        when(repo.save(asig)).thenReturn(asig);

        Asignatura out = service.update(1L, update);
        assertThat(out.getNombre()).isEqualTo("Matemáticas Avanzadas");
        assertThat(out.getActivo()).isFalse();
        verify(repo).save(asig);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(asig));
        service.delete(1L);
        verify(repo).delete(asig);
    }
}
