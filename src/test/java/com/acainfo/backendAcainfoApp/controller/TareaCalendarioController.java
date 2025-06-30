package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.TareaCalendario;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.TareaCalendarioService;
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
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TareaCalendarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TareaCalendarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TareaCalendarioService service;

    private TareaCalendario t1, t2;
    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().id(1L).nombre("Test").email("t@e.com").password("x").build();
        t1 = TareaCalendario.builder()
                .id(1L)
                .usuario(usuario)
                .titulo("T1")
                .descripcion("D1")
                .fecha(LocalDateTime.of(2025,1,1,10,0))
                .horaAviso(LocalTime.of(9,0))
                .completada(false)
                .build();
        t2 = TareaCalendario.builder()
                .id(2L)
                .usuario(usuario)
                .titulo("T2")
                .descripcion("D2")
                .fecha(LocalDateTime.of(2025,1,2,11,0))
                .horaAviso(LocalTime.of(10,0))
                .completada(false)
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(t1, t2));
        mvc.perform(get("/api/tareas-calendario"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(t1, t2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(t1);
        mvc.perform(get("/api/tareas-calendario/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_valid() throws Exception {
        TareaCalendario input = TareaCalendario.builder()
                .usuario(usuario)
                .titulo("N")
                .descripcion("D")
                .fecha(LocalDateTime.now())
                .build();
        TareaCalendario saved = TareaCalendario.builder()
                .id(3L)
                .usuario(usuario)
                .titulo("N")
                .descripcion("D")
                .fecha(input.getFecha())
                .build();
        when(service.create(any(TareaCalendario.class))).thenReturn(saved);

        mvc.perform(post("/api/tareas-calendario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/tareas-calendario/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        TareaCalendario updated = TareaCalendario.builder()
                .id(1L)
                .usuario(usuario)
                .titulo("Upd")
                .descripcion("UD")
                .fecha(LocalDateTime.of(2025,2,1,10,0))
                .completada(true)
                .build();
        when(service.update(eq(1L), any(TareaCalendario.class))).thenReturn(updated);

        mvc.perform(put("/api/tareas-calendario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.titulo").value("Upd"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());
        mvc.perform(delete("/api/tareas-calendario/1"))
           .andExpect(status().isNoContent());
    }
}
