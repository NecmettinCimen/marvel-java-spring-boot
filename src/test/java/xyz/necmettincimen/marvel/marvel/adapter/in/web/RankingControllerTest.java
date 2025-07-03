package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.Ranking;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

import org.springframework.http.MediaType;

public class RankingControllerTest extends BaseControllerTest {

        @Test
        void save_ranking() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                Ranking ranking = new Ranking();
                ranking.setUserId(user.getId());
                ranking.setTargetId("2");
                ranking.setTargetType("CHARACTER");
                ranking.setRankIndex(5);

                webTestClient.post()
                                .uri("/api/rankings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(ranking)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Ranking>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<Ranking> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult().getUserId().equals(user.getId());
                                });
        }

        @Test
        void get_user_rankings() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                webTestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/api/rankings/user")
                                                .queryParam("userId", user.getId())
                                                .queryParam("targetType", "CHARACTER")
                                                .build())
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBodyList(new ParameterizedTypeReference<ApiResponse<Ranking>>() {
                                })
                                .consumeWith(response -> {
                                        assert response.getResponseBody() != null;
                                });
        }

        @Test
        void get_auto_rankings() {
                LoginResponse loginResponse = register_login();
                webTestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/api/rankings/auto")
                                                .queryParam("targetType", "CHARACTER")
                                                .build())
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBodyList(new ParameterizedTypeReference<ApiResponse<Ranking>>() {
                                })
                                .consumeWith(response -> {
                                        assert response.getResponseBody() != null;
                                });
        }

        @Test
        void delete_ranking() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                Ranking ranking = new Ranking();
                ranking.setUserId(user.getId());
                ranking.setTargetId("2");
                ranking.setTargetType("CHARACTER");
                ranking.setRankIndex(5);

                Long[] rankingId = new Long[1];

                webTestClient.post()
                                .uri("/api/rankings")
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(ranking)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Ranking>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<Ranking> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        rankingId[0] = apiResponse.getResult().getId();
                                });

                webTestClient.delete()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/api/rankings")
                                                .queryParam("id", rankingId[0])
                                                .build())
                                .header("Authorization", "Bearer " + loginResponse.getToken())
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
