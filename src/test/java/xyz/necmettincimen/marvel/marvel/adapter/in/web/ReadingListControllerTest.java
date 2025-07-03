package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.ReadingListRequest;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

import org.springframework.http.MediaType;

public class ReadingListControllerTest extends BaseControllerTest {

        @Test
        void save_reading_list() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                ReadingList readingList = new ReadingList();
                readingList.setUserId(user.getId());
                readingList.setName("My Reading List");
                readingList.setDescription("A test reading list");
                
                ReadingListRequest request = new ReadingListRequest();
                request.setReadingList(readingList);

                webTestClient.post()
                                .uri("/api/readingLists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<ReadingList>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<ReadingList> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        assert apiResponse.getResult().getUserId().equals(user.getId());
                                });
        }

        @Test
        void get_user_reading_lists() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                webTestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/api/readingLists/{userId}")
                                                .queryParam("page", 0)
                                                .queryParam("pageSize", 10)
                                                .build(user.getId()))
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Object>>() {
                                })
                                .consumeWith(response -> {
                                        assert response.getResponseBody() != null;
                                });
        }

        @Test
        void get_reading_list_items() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                
                // First create a reading list
                ReadingList readingList = new ReadingList();
                readingList.setUserId(user.getId());
                readingList.setName("Test List");
                readingList.setDescription("Test Description");
                
                ReadingListRequest request = new ReadingListRequest();
                request.setReadingList(readingList);

                Long[] readingListId = new Long[1];

                webTestClient.post()
                                .uri("/api/readingLists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<ReadingList>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<ReadingList> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        readingListId[0] = apiResponse.getResult().getId();
                                });

                // Then get items for this reading list
                webTestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/api/readingLists/items/{readingListId}")
                                                .queryParam("page", 0)
                                                .queryParam("pageSize", 10)
                                                .build(readingListId[0]))
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<Object>>() {
                                })
                                .consumeWith(response -> {
                                        assert response.getResponseBody() != null;
                                });
        }

        @Test
        void delete_reading_list() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                
                // First create a reading list
                ReadingList readingList = new ReadingList();
                readingList.setUserId(user.getId());
                readingList.setName("Test List to Delete");
                readingList.setDescription("Test Description");
                
                ReadingListRequest request = new ReadingListRequest();
                request.setReadingList(readingList);

                ReadingList readingListToDelete = new ReadingList();

                webTestClient.post()
                                .uri("/api/readingLists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<ReadingList>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<ReadingList> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        readingList.setId(apiResponse.getResult().getId());
                                        readingList.setUserId(apiResponse.getResult().getUserId());
                                        readingList.setName(apiResponse.getResult().getName());
                                        readingList.setDescription(apiResponse.getResult().getDescription());
                                });

                // Then delete the reading list
                webTestClient.post()
                                .uri("/api/readingLists/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(readingList)
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

        @Test
        void delete_reading_list_item() {
                LoginResponse loginResponse = register_login();
                User user = loginResponse.getUser();
                
                // First create a reading list
                ReadingList readingList = new ReadingList();
                readingList.setUserId(user.getId());
                readingList.setName("Test List");
                readingList.setDescription("Test Description");
                
                ReadingListRequest request = new ReadingListRequest();
                request.setReadingList(readingList);

                Long[] readingListId = new Long[1];

                webTestClient.post()
                                .uri("/api/readingLists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(new ParameterizedTypeReference<ApiResponse<ReadingList>>() {
                                })
                                .consumeWith(response -> {
                                        ApiResponse<ReadingList> apiResponse = response.getResponseBody();
                                        assert apiResponse != null;
                                        assert apiResponse.isSuccess();
                                        readingListId[0] = apiResponse.getResult().getId();
                                });

                // Create a reading list item to delete
                ReadingListItem item = new ReadingListItem();
                item.setReadingListId(readingListId[0]);
                item.setTargetId("123");
                item.setTargetType("CHARACTER");

                // Delete the reading list item
                webTestClient.post()
                                .uri("/api/readingLists/deleteItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginResponse.getToken())
                                .bodyValue(item)
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
