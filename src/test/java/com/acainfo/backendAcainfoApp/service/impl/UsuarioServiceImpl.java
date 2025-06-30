package com.acainfo.backendAcainfoApp.service.impl;

import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder()
                .id(1L)
                .nombre("Pablo")
                .email("pablo@example.com")
                .carrera("CS")
                .curso(2)
                .metodoPago("tarjeta")
                .activo(true)
                .fotoUrl("http://foto")
                .build();
    }

    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(usuario));

        List<Usuario> list = service.findAll();

        assertThat(list).containsExactly(usuario);
        verify(repo).findAll();
    }

    @Test
    void findById_existing_returnsUsuario() {
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario found = service.findById(1L);

        assertThat(found).isSameAs(usuario);
    }

    @Test
    void findById_missing_throwsException() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(2L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Usuario not found with id 2");
    }

    @Test
    void create_new_savesAndReturns() {
        Usuario input = Usuario.builder()
                .nombre("Ana")
                .email("ana@example.com")
                .build();
        when(repo.findByEmail("ana@example.com")).thenReturn(Optional.empty());
        Usuario saved = Usuario.builder()
                .id(2L)
                .nombre("Ana")
                .email("ana@example.com")
                .build();
        when(repo.save(input)).thenReturn(saved);

        Usuario result = service.create(input);

        assertThat(result.getId()).isEqualTo(2L);
        verify(repo).save(input);
    }

    @Test
    void create_duplicateEmail_throwsException() {
        when(repo.findByEmail("pablo@example.com")).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> service.create(
                Usuario.builder().email("pablo@example.com").build()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email already in use");
    }

    @Test
    void update_existing_updatesAndReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario toUpdate = Usuario.builder()
                .nombre("Pablo2")
                .email("pablo2@example.com")
                .carrera("IT")
                .curso(3)
                .metodoPago("paypal")
                .activo(false)
                .fotoUrl("http://foto2")
                .build();
        when(repo.save(usuario)).thenReturn(usuario);

        Usuario updated = service.update(1L, toUpdate);

        assertThat(updated.getEmail()).isEqualTo("pablo2@example.com");
        verify(repo).save(usuario);
    }

    @Test
    void delete_existing_deletes() {
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));

        service.delete(1L);

        verify(repo).delete(usuario);
    }
}
