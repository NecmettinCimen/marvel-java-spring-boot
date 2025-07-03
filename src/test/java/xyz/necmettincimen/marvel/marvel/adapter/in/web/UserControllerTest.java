package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

public class UserControllerTest extends BaseControllerTest {

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
                                        assert apiResponse.isSuccess() == false;
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
                user.setPassword("null");
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
                                        assert apiResponse.isSuccess() == false;
                                });

                user.setId(0L);
                user.setEmail("mail_" + UUID.randomUUID().toString());
                webTestClient.put()
                                .uri("/api/users/update")
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .bodyValue(user)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<User>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<User> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult().getEmail()
                                                        .equals(user.getEmail());
                                });

                user.setId(result[0].getResult().getUser().getId() + 1);
                user.setPassword("password");
                webTestClient.put()
                                .uri("/api/users/update")
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .bodyValue(user)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<User>>() {
                                })
                                .consumeWith(response -> {
                                });

                webTestClient.get()
                                .uri("/api/users?page=0&pageSize=10")
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
                                .uri("/api/users/" + result[0].getResult().getUser().getId())
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
                webTestClient.get()
                                .uri("/api/users/" + (result[0].getResult().getUser().getId() + 1))
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<User>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<User> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess() == false;
                                });

                webTestClient.delete()
                                .uri("/api/users/" + result[0].getResult().getUser().getId())
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
