package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.UserFavoriteService;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.config.JwtUtil;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;

@RestController
@RequestMapping("/api/user/favorites")
public class UserFavoriteController {

    private final UserFavoriteService userFavoriteService;
    private final JwtUtil jwtUtil;

    public UserFavoriteController(UserFavoriteService userFavoriteService, JwtUtil jwtUtil) {
        this.userFavoriteService = userFavoriteService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Mono<ApiResponse<UserFavorite>> addFavorite(@RequestBody UserFavorite favorite, ServerWebExchange exchange) {
        favorite.setUserId(jwtUtil.extractId(exchange));
        favorite.setId(null);
        return userFavoriteService.addFavorite(favorite)
                .map(response -> new ApiResponse<>(response))
                .onErrorResume(response -> Mono
                        .just(new ApiResponse<>(false, "Error adding favorite: " + response.getMessage(), null)));
    }

    @GetMapping("/{userId}")
    public Mono<ApiResponse<List<UserFavorite>>> getFavorites(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            ServerWebExchange exchange) {
        if (userId == 0)
            userId = jwtUtil.extractId(exchange);
        return userFavoriteService.getFavorites(userId, page, pageSize)
                .collectList()
                .map(favorites -> new ApiResponse<List<UserFavorite>>(favorites));

    }

    @PostMapping("/delete")
    public Mono<ApiResponse<Void>> deleteFavorite(@RequestBody UserFavorite favorite, ServerWebExchange exchange) {
        favorite.setUserId(jwtUtil.extractId(exchange));
        return userFavoriteService.deleteFavorite(favorite)
                .then(Mono.just(new ApiResponse<Void>(true, "Favorite deleted successfully", null)))
                .onErrorResume(e -> Mono
                        .just(new ApiResponse<Void>(false, "Error deleting favorite: " + e.getMessage(), null)));
    }

}
