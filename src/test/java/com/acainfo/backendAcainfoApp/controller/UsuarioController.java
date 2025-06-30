package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UsuarioService service;

    private Usuario u1, u2;

    @BeforeEach
    void setup() {
        u1 = Usuario.builder()
                .id(1L)
                .nombre("Pablo")
                .email("pablo@example.com")
                .carrera("CS")
                .curso(2)
                .metodoPago("tarjeta")
                .activo(true)
                .fotoUrl("http://foto1")
                .build();
        u2 = Usuario.builder()
                .id(2L)
                .nombre("Ana")
                .email("ana@example.com")
                .carrera("ENG")
                .curso(1)
                .metodoPago("paypal")
                .activo(true)
                .fotoUrl("http://foto2")
                .build();
    }

    @Test
    void list_returnsAllUsers() throws Exception {
        when(service.findAll()).thenReturn(List.of(u1, u2));

        mvc.perform(get("/api/usuarios"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(u1, u2))));
    }

    @Test
    void getById_found_returnsUser() throws Exception {
        when(service.findById(1L)).thenReturn(u1);

        mvc.perform(get("/api/usuarios/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.email").value("pablo@example.com"));
    }

    @Test
    void create_valid_createsUser() throws Exception {
        Usuario input = Usuario.builder()
                .nombre("Carlos")
                .email("carlos@example.com")
                .build();
        Usuario saved = Usuario.builder()
                .id(3L)
                .nombre("Carlos")
                .email("carlos@example.com")
                .build();
        when(service.create(any(Usuario.class))).thenReturn(saved);

        mvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/usuarios/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid_updatesUser() throws Exception {
        Usuario updated = Usuario.builder()
                .id(1L)
                .nombre("PabloUpdated")
                .email("pablo2@example.com")
                .build();
        when(service.update(eq(1L), any(Usuario.class))).thenReturn(updated);

        mvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("PabloUpdated"))
           .andExpect(jsonPath("$.email").value("pablo2@example.com"));
    }

    @Test
    void delete_existing_noContent() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(delete("/api/usuarios/1"))
           .andExpect(status().isNoContent());
    }
}
