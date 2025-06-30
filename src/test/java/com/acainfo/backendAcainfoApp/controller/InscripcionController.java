package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Inscripcion;
import com.acainfo.backendAcainfoApp.service.InscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InscripcionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class InscripcionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private InscripcionService service;

    private Inscripcion i1, i2;

    @BeforeEach
    void setup() {
        i1 = Inscripcion.builder()
                .id(1L)
                .fechaAlta(LocalDateTime.of(2025,6,1,10,0))
                .estado("ACTIVE")
                .build();
        i2 = Inscripcion.builder()
                .id(2L)
                .fechaAlta(LocalDateTime.of(2025,6,2,11,30))
                .estado("PENDING")
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(i1, i2));

        mvc.perform(get("/api/inscripciones"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(i1, i2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(i1);

        mvc.perform(get("/api/inscripciones/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.estado").value("ACTIVE"));
    }

    @Test
    void create_valid() throws Exception {
        Inscripcion input = Inscripcion.builder()
                .fechaAlta(LocalDateTime.now())
                .estado("PENDING")
                .build();
        Inscripcion saved = Inscripcion.builder()
                .id(3L)
                .fechaAlta(input.getFechaAlta())
                .estado("PENDING")
                .build();
        when(service.create(any(Inscripcion.class))).thenReturn(saved);

        mvc.perform(post("/api/inscripciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/inscripciones/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        Inscripcion updated = Inscripcion.builder()
                .id(1L)
                .fechaAlta(i1.getFechaAlta())
                .fechaBaja(LocalDateTime.now().plusDays(2))
                .estado("CANCELLED")
                .build();
        when(service.update(eq(1L), any(Inscripcion.class))).thenReturn(updated);

        mvc.perform(put("/api/inscripciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.estado").value("CANCELLED"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(delete("/api/inscripciones/1"))
           .andExpect(status().isNoContent());
    }
}