package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import com.acainfo.backendAcainfoApp.domain.Horario;
import com.acainfo.backendAcainfoApp.domain.Profesor;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.HorarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HorarioController.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class HorarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private HorarioService service;

    private Horario h1, h2;
    private Asignatura asig;
    private Profesor prof;

    @BeforeEach
    void setup() {
        asig = Asignatura.builder().id(1L).nombre("Mat").build();
        Usuario usuario = Usuario.builder().id(2L).nombre("Profe").email("p@e.com").password("x").build();
        prof = Profesor.builder().id(2L).usuario(usuario).especialidad("mat").build();
        h1 = Horario.builder()
                .id(1L)
                .asignatura(asig)
                .profesor(prof)
                .fecha(LocalDate.of(2025,1,1))
                .horaInicio(LocalTime.of(8,0))
                .horaFin(LocalTime.of(10,0))
                .build();
        h2 = Horario.builder()
                .id(2L)
                .asignatura(asig)
                .profesor(prof)
                .fecha(LocalDate.of(2025,1,2))
                .horaInicio(LocalTime.of(10,0))
                .horaFin(LocalTime.of(12,0))
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(h1, h2));

        mvc.perform(get("/api/horarios"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(h1, h2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(h1);

        mvc.perform(get("/api/horarios/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_valid() throws Exception {
        Horario input = Horario.builder()
                .asignatura(asig)
                .profesor(prof)
                .fecha(LocalDate.of(2025,2,1))
                .horaInicio(LocalTime.of(9,0))
                .horaFin(LocalTime.of(11,0))
                .build();
        Horario saved = Horario.builder()
                .id(3L)
                .asignatura(asig)
                .profesor(prof)
                .fecha(input.getFecha())
                .horaInicio(input.getHoraInicio())
                .horaFin(input.getHoraFin())
                .build();
        when(service.create(any(Horario.class))).thenReturn(saved);

        mvc.perform(post("/api/horarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/horarios/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        Horario updated = Horario.builder()
                .id(1L)
                .asignatura(asig)
                .profesor(prof)
                .fecha(LocalDate.of(2025,3,1))
                .horaInicio(LocalTime.of(10,0))
                .horaFin(LocalTime.of(12,0))
                .build();
        when(service.update(eq(1L), any(Horario.class))).thenReturn(updated);

        mvc.perform(put("/api/horarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.fecha").value("2025-03-01"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(delete("/api/horarios/1"))
           .andExpect(status().isNoContent());
    }

    @Test
    void listByAsignatura_returnsHorarios() throws Exception {
        when(service.findByAsignaturaId(1L)).thenReturn(List.of(h1));

        mvc.perform(get("/api/horarios/asignatura/1"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(h1))));
    }

    @Test
    void listByProfesor_returnsHorarios() throws Exception {
        when(service.findByProfesorId(2L)).thenReturn(List.of(h1, h2));

        mvc.perform(get("/api/horarios/profesor/2"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(h1, h2))));
    }

    @Test
    void listByFecha_returnsHorarios() throws Exception {
        when(service.findByFecha(LocalDate.of(2025,1,1))).thenReturn(List.of(h1));

        mvc.perform(get("/api/horarios/fecha/2025-01-01"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(h1))));
    }
}
