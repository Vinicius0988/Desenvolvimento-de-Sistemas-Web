package com.agencia.travelapi;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ActiveProfiles("test")
class TravelApiApplicationTests {

    @Test
    void contextLoads() {
        // Verifica se o contexto do Spring sobe corretamente,
        // garantindo que todos os beans (controller, service, repository)
        // estao configurados sem erros.
    }

}
