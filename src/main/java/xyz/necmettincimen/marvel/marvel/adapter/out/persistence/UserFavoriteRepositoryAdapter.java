package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;
import xyz.necmettincimen.marvel.marvel.domain.port.UserFavoriteRepositoryPort;

@Component
public class UserFavoriteRepositoryAdapter implements UserFavoriteRepositoryPort {
    private final UserFavoriteR2dbcRepository repository;

    public UserFavoriteRepositoryAdapter(UserFavoriteR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<UserFavorite> save(UserFavorite favorite) {
        return repository.save(favorite);
    }

    @Override
    public Flux<UserFavorite> findAllByUserId(Long userId, int page, int pageSize) {
        return repository.findAllByUserId(userId)
                .skip(page * pageSize)
                .take(pageSize);
    }

    @Override
    public Mono<Void> delete(UserFavorite favorite) {
        return repository.delete(favorite);
    }

}
