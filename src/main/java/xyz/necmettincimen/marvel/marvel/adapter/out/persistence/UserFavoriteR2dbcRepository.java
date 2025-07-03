package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import xyz.necmettincimen.marvel.marvel.domain.model.UserFavorite;

@Repository
public interface UserFavoriteR2dbcRepository extends ReactiveCrudRepository<UserFavorite, Long> {
    Flux<UserFavorite> findAllByUserId(Long userId);    
}
