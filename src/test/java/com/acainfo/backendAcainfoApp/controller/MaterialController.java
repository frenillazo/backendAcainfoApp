package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Material;
import com.acainfo.backendAcainfoApp.service.MaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaterialController.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class MaterialControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private MaterialService service;

    private Material m1, m2;

    @BeforeEach
    void setup() {
        m1 = Material.builder()
                .id(1L)
                .titulo("Apuntes")
                .url("http://a")
                .tipo("PDF")
                .build();
        m2 = Material.builder()
                .id(2L)
                .titulo("Vídeo")
                .url("http://v")
                .tipo("VIDEO")
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(m1, m2));

        mvc.perform(get("/api/materiales"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(m1, m2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(m1);

        mvc.perform(get("/api/materiales/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.titulo").value("Apuntes"));
    }

    @Test
    void create_valid() throws Exception {
        Material input = Material.builder()
                .titulo("Doc")
                .url("http://d")
                .tipo("DOC")
                .build();
        Material saved = Material.builder()
                .id(3L)
                .titulo("Doc")
                .url("http://d")
                .tipo("DOC")
                .build();
        when(service.create(any(Material.class))).thenReturn(saved);

        mvc.perform(post("/api/materiales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/materiales/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        Material updated = Material.builder()
                .id(1L)
                .titulo("Nuevo")
                .url("http://n")
                .tipo("PDF")
                .build();
        when(service.update(eq(1L), any(Material.class))).thenReturn(updated);

        mvc.perform(put("/api/materiales/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.titulo").value("Nuevo"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(delete("/api/materiales/1"))
           .andExpect(status().isNoContent());
    }
}
