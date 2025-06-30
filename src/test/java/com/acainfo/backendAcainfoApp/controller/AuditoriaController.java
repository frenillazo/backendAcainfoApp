package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Auditoria;
import com.acainfo.backendAcainfoApp.service.AuditoriaService;
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

@WebMvcTest(AuditoriaController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for testing
class AuditoriaControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AuditoriaService service;

    private Auditoria a1, a2;

    @BeforeEach
    void setup() {
        a1 = Auditoria.builder()
                .idAuditoria(1L)
                .tabla("usuario")
                .idRegistro(2L)
                .operacion("INSERT")
                .datosNuevos("{}")
                .usuario("admin")
                .timestamp(LocalDateTime.now())
                .build();
        a2 = Auditoria.builder()
                .idAuditoria(2L)
                .tabla("usuario")
                .idRegistro(3L)
                .operacion("DELETE")
                .datosNuevos("{}")
                .usuario("admin")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(a1, a2));
        mvc.perform(get("/api/auditorias"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(a1, a2))));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(a1);
        mvc.perform(get("/api/auditorias/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.idAuditoria").value(1));
    }

    @Test
    void create_valid() throws Exception {
        Auditoria input = Auditoria.builder()
                .tabla("usuario")
                .idRegistro(4L)
                .operacion("INSERT")
                .datosNuevos("{}").usuario("admin")
                .timestamp(a1.getTimestamp())
                .build();
        Auditoria saved = Auditoria.builder()
                .idAuditoria(3L)
                .tabla("usuario")
                .idRegistro(4L)
                .operacion("INSERT")
                .datosNuevos("{}").usuario("admin")
                .timestamp(a1.getTimestamp())
                .build();
        when(service.create(any(Auditoria.class))).thenReturn(saved);

        mvc.perform(post("/api/auditorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/auditorias/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid() throws Exception {
        Auditoria updated = Auditoria.builder()
                .idAuditoria(1L)
                .tabla("usuario")
                .idRegistro(2L)
                .operacion("UPDATE")
                .datosNuevos("{}").usuario("admin")
                .timestamp(a1.getTimestamp())
                .build();
        when(service.update(eq(1L), any(Auditoria.class))).thenReturn(updated);

        mvc.perform(put("/api/auditorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.operacion").value("UPDATE"));
    }

    @Test
    void delete_existing() throws Exception {
        doNothing().when(service).delete(anyLong());
        mvc.perform(delete("/api/auditorias/1"))
           .andExpect(status().isNoContent());
    }
}
