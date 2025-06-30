package com.acainfo.backendAcainfoApp.controller;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureMockMvc(addFilters = false)   // o quita esta línea y usa with(csrf()) según opción
@WithMockUser
@ActiveProfiles("test")
public abstract class BaseControllerTest { }
