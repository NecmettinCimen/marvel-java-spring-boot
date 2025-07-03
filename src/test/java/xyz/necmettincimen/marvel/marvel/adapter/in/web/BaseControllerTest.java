package xyz.necmettincimen.marvel.marvel.adapter.in.web;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {
        @Value("${local.server.port}")
        private int port;

        protected WebTestClient webTestClient;

        @BeforeEach
        void setup() {
                this.webTestClient = WebTestClient.bindToServer()
                                .baseUrl("http://localhost:" + port)
                                .build();
        }
      
}
