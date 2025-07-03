package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Rating;

@Repository
public interface RatingR2dbcRepository extends ReactiveCrudRepository<Rating, Long> {
    Mono<Rating> findByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, String targetId);
    Flux<Rating> findAllByTargetTypeAndTargetId(String targetType, String targetId);
}
