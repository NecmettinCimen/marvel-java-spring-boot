package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;

public class UserFavoriteControllerTest extends BaseControllerTest {

        @Test
        void full_flow() {

                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();

                final ApiResponse<UserFavorite>[] favoriteResult = new ApiResponse[1];
                webTestClient.post()
                                .uri("/api/user/favorites")
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(new UserFavorite(user.getId(), "comic", "1"))
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
                                .uri("/api/user/favorites/" + favoriteResult[0].getResult().getUserId()+"?page=0&pageSize=10")
                                .header("Authorization", "Bearer " + loginResponse.getToken())
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
                                .uri("/api/user/favorites/0?page=0&pageSize=10")
                                .header("Authorization", "Bearer " + loginResponse.getToken())
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
                                .header("Authorization", "Bearer " + loginResponse.getToken())
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
