package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Notificacion;
import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for testing
class NotificacionControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private NotificacionService service;

    private Notificacion n1, n2;
    private Usuario user;

    @BeforeEach
    void setup() {
        user = Usuario.builder().id(1L).nombre("U").email("u@e.com").password("p").build();
        n1 = Notificacion.builder()
                .idNotificacion(1L)
                .usuario(user)
                .tipo("EMAIL")
                .titulo("T1")
                .cuerpo("C1")
                .enviadaEn(LocalDateTime.now())
                .leida(false)
                .build();
        n2 = Notificacion.builder()
                .idNotificacion(2L)
                .usuario(user)
                .tipo("PUSH")
                .titulo("T2")
                .cuerpo("C2")
                .enviadaEn(LocalDateTime.now())
                .leida(true)
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(n1, n2));
        mvc.perform(get("/api/notificaciones"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(n1, n2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(n1);
        mvc.perform(get("/api/notificaciones/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.idNotificacion").value(1));
    }

    @Test
    void create_valid() throws Exception {
        Notificacion input = Notificacion.builder()
                .usuario(user)
                .tipo("EMAIL")
                .titulo("T")
                .cuerpo("C")
                .enviadaEn(n1.getEnviadaEn())
                .leida(false)
                .build();
        Notificacion saved = Notificacion.builder()
                .idNotificacion(3L)
                .usuario(user)
                .tipo("EMAIL")
                .titulo("T")
                .cuerpo("C")
                .enviadaEn(n1.getEnviadaEn())
                .leida(false)
                .build();
        when(service.create(any(Notificacion.class))).thenReturn(saved);

        mvc.perform(post("/api/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/notificaciones/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        Notificacion updated = Notificacion.builder()
                .idNotificacion(1L)
                .usuario(user)
                .tipo("PUSH")
                .titulo("U")
                .cuerpo("C")
                .enviadaEn(n1.getEnviadaEn())
                .leida(true)
                .build();
        when(service.update(eq(1L), any(Notificacion.class))).thenReturn(updated);

        mvc.perform(put("/api/notificaciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.tipo").value("PUSH"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());
        mvc.perform(delete("/api/notificaciones/1"))
           .andExpect(status().isNoContent());
    }
}
