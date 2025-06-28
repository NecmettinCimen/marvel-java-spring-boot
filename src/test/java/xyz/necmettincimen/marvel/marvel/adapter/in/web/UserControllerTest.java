package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
        @Value("${local.server.port}")
        private int port;

        private WebTestClient webTestClient;

        @BeforeEach
        void setup() {
                this.webTestClient = WebTestClient.bindToServer()
                                .baseUrl("http://localhost:" + port)
                                .build();
        }

        @Test
        void full_flow() {

                User user = new User(
                                "user_" + UUID.randomUUID().toString(),
                                "pass_" + UUID.randomUUID().toString(),
                                "mail_" + UUID.randomUUID().toString());

                webTestClient.post()
                                .uri("/api/users/public/register")
                                .bodyValue(user)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<User>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<User> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult().getUsername().equals(user.getUsername());
                                });

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
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult().getUser().getUsername()
                                                        .equals(user.getUsername());
                                        result[0] = apiResponse;
                                });

                webTestClient.get()
                                .uri("/api/users")
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<List<User>>>() {
                                        
                                })
                                .consumeWith(response -> {
                                        ApiResponse<List<User>> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                        assert !apiResponse.getResult().isEmpty();
                                        assert apiResponse.getResult().stream()
                                                        .anyMatch(u -> u.getUsername().equals(user.getUsername()));
                                });

                webTestClient.get()
                                .uri("/api/users/"+result[0].getResult().getUser().getId())
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .exchange()
                                .expectStatus().isOk()
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
                webTestClient.delete()
                                .uri("/api/users/"+result[0].getResult().getUser().getId())
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<User>>() {
                                        
                                })
                                .consumeWith(response -> {
                                        ApiResponse<User> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                });
        }

}
