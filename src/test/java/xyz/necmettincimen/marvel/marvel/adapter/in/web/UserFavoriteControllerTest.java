package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;

public class UserFavoriteControllerTest extends BaseControllerTest {

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

                final ApiResponse<UserFavorite>[] favoriteResult = new ApiResponse[1];
                webTestClient.post()
                                .uri("/api/user/favorites")
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .bodyValue(new UserFavorite(result[0].getResult().getUser().getId(), "comic", "1"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<UserFavorite>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<UserFavorite> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                        favoriteResult[0] = apiResponse;
                                });

                webTestClient.get()
                                .uri("/api/user/favorites/" + favoriteResult[0].getResult().getUserId())
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<List<UserFavorite>>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<List<UserFavorite>> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                });
                                
                webTestClient.get()
                                .uri("/api/user/favorites/0")
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<List<UserFavorite>>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<List<UserFavorite>> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                });

                webTestClient.post()
                                .uri("/api/user/favorites/delete")
                                .header("Authorization", "Bearer " + result[0].getResult().getToken())
                                .bodyValue(favoriteResult[0].getResult())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Void>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<Void> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                });
        }

}
