package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.UserService;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

@WebFluxTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@gmail.com");
        when(userService.getAllUsers()).thenReturn(Flux.just(user));

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(1)
                .contains(user);
    }

    @Test
    void registerUser_shouldReturnCreatedUser() {
        User user = new User();
        user.setId(null);
        user.setUsername("testuser");
        user.setEmail("test@gmail.com");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("test@gmail.com");
        when(userService.registerUser(user)).thenReturn(Mono.just(savedUser));

        webTestClient.post()
                .uri("api/users/register")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(savedUser);
    }
}
