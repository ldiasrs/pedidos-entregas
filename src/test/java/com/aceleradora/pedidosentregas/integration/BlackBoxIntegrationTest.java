package com.aceleradora.pedidosentregas.integration;

import com.aceleradora.pedidosentregas.PedidosEntregasApplication;
import com.aceleradora.pedidosentregas.controller.PathMappings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Disabled
@TestConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(SpringExtension.class)
@Profile("dev")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PedidosEntregasApplication.class)
@ContextConfiguration()
public class BlackBoxIntegrationTest {

        @LocalServerPort
        private String port;

        @Test
        public void shouldAutheticateUsingJWT()  {
                String url = "http://localhost:" + this.port + PathMappings.getFullPath(PathMappings.AUTH_MAPPING);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", " Basic bXl1c2VyOnNlbmhhbG9jYWw=");
                TestRestTemplate testRestTemplate = new TestRestTemplate();
                HttpEntity<String> stringHttpEntity = new HttpEntity<>("", headers);
                ResponseEntity<String> response = testRestTemplate.exchange(
                        url, HttpMethod.GET, stringHttpEntity, String.class);
                Assertions.assertNotNull(response);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                Assertions.assertNotNull(response.getHeaders().get("Authorization"));
        }
}
