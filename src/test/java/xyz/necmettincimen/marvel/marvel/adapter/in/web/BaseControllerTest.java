package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

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

        protected LoginResponse register_login() {
                User user = new User(
                                "test_user",
                                "string");

                final ApiResponse<LoginResponse>[] result = new ApiResponse[1];
                webTestClient.post()
                                .uri("/api/users/public/login")
                                .bodyValue(user)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<LoginResponse>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<LoginResponse> apiResponse = response.getResponseBody();
                                        result[0] = apiResponse;
                                });
                if (result[0] == null || !result[0].isSuccess()) {
                        webTestClient.post()
                                        .uri("/api/users/public/register")
                                        .bodyValue(user)
                                        .exchange()
                                        .expectBody(new ParameterizedTypeReference<ApiResponse<User>>() {
                                        })
                                        .consumeWith(response -> {
                                                ApiResponse<User> apiResponse = response.getResponseBody();
                                                assert apiResponse != null;
                                                assert apiResponse.isSuccess();
                                                assert apiResponse.getResult() != null;
                                                assert apiResponse.getResult().getUsername()
                                                                .equals(user.getUsername());
                                        });
                        return register_login();
                }

                return result[0].getResult();
        }

}
