package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.Rating;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

public class RatingControllerTest extends BaseControllerTest {

        @Test
        void full_flow() {

                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();

                final ApiResponse<Rating>[] favoriteResult = new ApiResponse[1];
                webTestClient.post()
                                .uri("/api/ratings")
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(new Rating(user.getId(), "comic", "1", 2))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Rating>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<Rating> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                        favoriteResult[0] = apiResponse;
                                });

                webTestClient.get()
                                .uri("/api/ratings/average?targetType=" + favoriteResult[0].getResult().getTargetType()
                                                + "&targetId=" + favoriteResult[0].getResult().getTargetId())
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Double>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<Double> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                });
                webTestClient.get()
                                .uri("/api/ratings/average?targetType=CHARACTER&targetId=0")
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Double>>() {

                                })
                                .consumeWith(response -> {
                                        ApiResponse<Double> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult() != null;
                                });

        }

}
