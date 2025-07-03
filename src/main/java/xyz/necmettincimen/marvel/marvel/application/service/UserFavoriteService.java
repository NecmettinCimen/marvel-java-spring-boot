package xyz.necmettincimen.marvel.marvel.application.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;
import xyz.necmettincimen.marvel.marvel.domain.port.UserFavoriteRepositoryPort;

@Service
public class UserFavoriteService  {
    private final UserFavoriteRepositoryPort repositoryPort;
    public UserFavoriteService(UserFavoriteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Mono<UserFavorite> addFavorite(UserFavorite favorite){
        return repositoryPort.save(favorite);
    }

    public Flux<UserFavorite> getFavorites(Long userId, int page, int pageSize){
        return repositoryPort.findAllByUserId(userId, page, pageSize);
    }

    public Mono<Void> deleteFavorite(UserFavorite favorite){
        return repositoryPort.delete(favorite);
    }
}
