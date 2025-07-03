package xyz.necmettincimen.marvel.marvel.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Rating;

public interface RatingRepositoryPort {
    Mono<Rating> save(Rating rating);
    Mono<Rating> findByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, String targetId);
    Flux<Rating> findAllByTargetTypeAndTargetId(String targetType, String targetId);
}
