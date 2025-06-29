package com.acainfo.backendAcainfoApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")        // << activa application-test.properties
class BackendAcainfoAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
