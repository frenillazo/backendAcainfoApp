package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import com.acainfo.backendAcainfoApp.service.AsignaturaService;
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

@WebMvcTest(AsignaturaController.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class AsignaturaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AsignaturaService service;

    private Asignatura a1, a2;

    @BeforeEach
    void setup() {
        a1 = Asignatura.builder()
                .id(1L)
                .nombre("Matemáticas")
                .carrera("CS")
                .curso(1)
                .cuatrimestre(1)
                .descripcion("Álgebra")
                .activo(true)
                .build();
        a2 = Asignatura.builder()
                .id(2L)
                .nombre("Física")
                .carrera("CS")
                .curso(1)
                .cuatrimestre(2)
                .descripcion("Mecánica")
                .activo(true)
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(a1, a2));

        mvc.perform(get("/api/asignaturas"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(a1, a2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(a1);

        mvc.perform(get("/api/asignaturas/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.nombre").value("Matemáticas"));
    }

    @Test
    void create_valid_createsAsignatura() throws Exception {
        Asignatura input = Asignatura.builder()
                .nombre("Química")
                .carrera("CS")
                .curso(2)
                .cuatrimestre(1)
                .descripcion("Orgánica")
                .activo(true)
                .build();
        Asignatura saved = Asignatura.builder()
                .id(3L)
                .nombre("Química")
                .carrera("CS")
                .curso(2)
                .cuatrimestre(1)
                .descripcion("Orgánica")
                .activo(true)
                .build();
        when(service.create(any(Asignatura.class))).thenReturn(saved);

        mvc.perform(post("/api/asignaturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/asignaturas/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid_updatesAsignatura() throws Exception {
        Asignatura updated = Asignatura.builder()
                .id(1L)
                .nombre("Matemáticas II")
                .carrera("CS")
                .curso(2)
                .cuatrimestre(1)
                .descripcion("Cálculo")
                .activo(false)
                .build();
        when(service.update(eq(1L), any(Asignatura.class))).thenReturn(updated);

        mvc.perform(put("/api/asignaturas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("Matemáticas II"))
           .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void delete_existing_noContent() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(delete("/api/asignaturas/1"))
           .andExpect(status().isNoContent());
    }
}
