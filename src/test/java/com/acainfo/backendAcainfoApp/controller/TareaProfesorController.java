package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Profesor;
import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.TareaProfesorService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TareaProfesorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TareaProfesorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TareaProfesorService service;

    private TareaProfesor t1, t2;
    private Profesor profesor;

    @BeforeEach
    void setup() {
        Usuario usuario = Usuario.builder().id(2L).nombre("Profe").email("p@e.com").password("x").build();
        profesor = Profesor.builder().id(2L).usuario(usuario).especialidad("mat").build();
        t1 = TareaProfesor.builder()
                .idTarea(1L)
                .profesor(profesor)
                .titulo("T1")
                .descripcion("D1")
                .fechaCreacion(LocalDateTime.of(2025,1,1,10,0))
                .fechaEjecucion(LocalDateTime.of(2025,2,1,10,0))
                .visibilidad("ALL")
                .build();
        t2 = TareaProfesor.builder()
                .idTarea(2L)
                .profesor(profesor)
                .titulo("T2")
                .descripcion("D2")
                .fechaCreacion(LocalDateTime.of(2025,1,2,10,0))
                .fechaEjecucion(LocalDateTime.of(2025,2,2,10,0))
                .visibilidad("ALL")
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(t1, t2));
        mvc.perform(get("/api/tareas-profesor"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(t1, t2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(t1);
        mvc.perform(get("/api/tareas-profesor/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.idTarea").value(1));
    }

    @Test
    void create_valid() throws Exception {
        TareaProfesor input = TareaProfesor.builder()
                .profesor(profesor)
                .titulo("N")
                .descripcion("D")
                .fechaCreacion(LocalDateTime.now())
                .fechaEjecucion(LocalDateTime.now())
                .visibilidad("ALL")
                .build();
        TareaProfesor saved = TareaProfesor.builder()
                .idTarea(3L)
                .profesor(profesor)
                .titulo("N")
                .descripcion("D")
                .fechaCreacion(input.getFechaCreacion())
                .fechaEjecucion(input.getFechaEjecucion())
                .visibilidad("ALL")
                .build();
        when(service.create(any(TareaProfesor.class))).thenReturn(saved);

        mvc.perform(post("/api/tareas-profesor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/tareas-profesor/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        TareaProfesor updated = TareaProfesor.builder()
                .idTarea(1L)
                .profesor(profesor)
                .titulo("Upd")
                .descripcion("UD")
                .fechaCreacion(LocalDateTime.now())
                .fechaEjecucion(LocalDateTime.now())
                .visibilidad("CUSTOM")
                .build();
        when(service.update(eq(1L), any(TareaProfesor.class))).thenReturn(updated);

        mvc.perform(put("/api/tareas-profesor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.visibilidad").value("CUSTOM"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());
        mvc.perform(delete("/api/tareas-profesor/1"))
           .andExpect(status().isNoContent());
    }
}
