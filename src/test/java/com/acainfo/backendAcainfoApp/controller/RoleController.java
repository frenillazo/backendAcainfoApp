package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Role;
import com.acainfo.backendAcainfoApp.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;



import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
@WithMockUser
class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private RoleService service;

    @Autowired
    private ObjectMapper mapper;

    private Role admin, user;

    @BeforeEach
    void setup() {
        admin = new Role(1L, "ROLE_ADMIN");
        user  = new Role(2L, "ROLE_USER");
    }

    @Test
    void list_returnsAllRoles() throws Exception {
        when(service.findAll()).thenReturn(List.of(admin, user));

        mvc.perform(get("/api/roles"))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(List.of(admin, user))));
    }

    @Test
    void getById_found_returnsRole() throws Exception {
        when(service.findById(1L)).thenReturn(admin);

        mvc.perform(get("/api/roles/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.name").value("ROLE_ADMIN"));
    }

    @Test
    void create_valid_createsRole() throws Exception {
        Role input = new Role(null, "ROLE_NEW");
        Role saved = new Role(3L, "ROLE_NEW");
        when(service.create(any(Role.class))).thenReturn(saved);

        mvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/api/roles/3"))
           .andExpect(content().json(mapper.writeValueAsString(saved)));
    }

    @Test
    void update_valid_updatesRole() throws Exception {
        Role updated = new Role(1L, "ROLE_CHANGED");
        when(service.update(eq(1L), any())).thenReturn(updated);

        mvc.perform(put("/api/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("ROLE_CHANGED"));
    }

    @Test
    void delete_existing_noContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/roles/1"))
           .andExpect(status().isNoContent());
    }
}