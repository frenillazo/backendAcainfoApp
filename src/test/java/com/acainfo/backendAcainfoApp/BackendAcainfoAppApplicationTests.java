package com.acainfo.backendAcainfoApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")                   // Carga application-test.properties
@AutoConfigureMockMvc(addFilters = false) // Desactiva Spring Security en el test
class BackendAcainfoAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
