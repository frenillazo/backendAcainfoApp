package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Material;
import com.acainfo.backendAcainfoApp.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaterialServiceImplTest {

    @Mock
    private MaterialRepository repo;

    @InjectMocks
    private MaterialServiceImpl service;

    private Material mat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mat = Material.builder()
                .id(1L)
                .titulo("Apuntes Álgebra")
                .url("http://.../algebra.pdf")
                .tipo("PDF")
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(mat));
        List<Material> list = service.findAll();
        assertThat(list).containsExactly(mat);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsMaterial() {
        when(repo.findById(1L)).thenReturn(Optional.of(mat));
        Material found = service.findById(1L);
        assertThat(found).isSameAs(mat);
    }

    @Test
    void findById_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(2L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Material not found with id 2");
    }

    @Test
    void create_savesAndReturns() {
        Material input = Material.builder()
                .titulo("Vídeo Clase")
                .url("http://.../clase1.mp4")
                .tipo("VIDEO")
                .build();
        Material saved = Material.builder()
                .id(2L)
                .titulo(input.getTitulo())
                .url(input.getUrl())
                .tipo(input.getTipo())
                .build();
        when(repo.save(input)).thenReturn(saved);

        Material result = service.create(input);
        assertThat(result.getId()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(mat));
        Material update = Material.builder()
                .titulo("Apuntes Avanzados")
                .url("http://.../avanzado.pdf")
                .tipo("PDF")
                .build();
        when(repo.save(mat)).thenReturn(mat);

        Material out = service.update(1L, update);
        assertThat(out.getTitulo()).isEqualTo("Apuntes Avanzados");
        verify(repo).save(mat);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(mat));
        service.delete(1L);
        verify(repo).delete(mat);
    }
}
