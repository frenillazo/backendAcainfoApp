package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumno;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumnoId;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.TareaProfesorAlumnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TareaProfesorAlumnoController.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class TareaProfesorAlumnoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TareaProfesorAlumnoService service;

    private TareaProfesorAlumno tpa;
    private TareaProfesor tareaProfesor;
    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().id(1L).nombre("A").email("a@e.com").password("x").build();
        tareaProfesor = TareaProfesor.builder().idTarea(1L).build();
        tpa = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("PENDING")
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(tpa));
        mvc.perform(get("/api/tareas-profesor-alumno"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(tpa))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(new TareaProfesorAlumnoId(1L,1L))).thenReturn(tpa);
        mvc.perform(get("/api/tareas-profesor-alumno/1/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.estado").value("PENDING"));
    }

    @Test
    void create_valid() throws Exception {
        TareaProfesorAlumno input = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("PENDING")
                .build();
        when(service.create(any(TareaProfesorAlumno.class))).thenReturn(input);

        mvc.perform(post("/api/tareas-profesor-alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/tareas-profesor-alumno/1/1"))
           .andExpect(content().json(mapper.writeValueAsString(input)));
    }

    @Test
    void update_valid() throws Exception {
        TareaProfesorAlumno updated = TareaProfesorAlumno.builder()
                .idTarea(1L)
                .idUsuario(1L)
                .tareaProfesor(tareaProfesor)
                .usuario(usuario)
                .estado("COMPLETED")
                .build();
        when(service.update(eq(new TareaProfesorAlumnoId(1L,1L)), any(TareaProfesorAlumno.class))).thenReturn(updated);

        mvc.perform(put("/api/tareas-profesor-alumno/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.estado").value("COMPLETED"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(any());
        mvc.perform(delete("/api/tareas-profesor-alumno/1/1"))
           .andExpect(status().isNoContent());
    }
}
