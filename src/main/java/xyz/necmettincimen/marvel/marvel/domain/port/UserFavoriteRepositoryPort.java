package xyz.necmettincimen.marvel.marvel.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;

public interface UserFavoriteRepositoryPort {
    Mono<UserFavorite> save(UserFavorite favorite);
    Flux<UserFavorite> findAllByUserId(Long userId, int page, int pageSize);
    Mono<Void> delete(UserFavorite favorite);
}
