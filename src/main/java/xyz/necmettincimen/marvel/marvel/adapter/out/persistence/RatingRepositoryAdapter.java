package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Rating;
import xyz.necmettincimen.marvel.marvel.domain.port.RatingRepositoryPort;

@Component
public class RatingRepositoryAdapter implements RatingRepositoryPort {
    private final RatingR2dbcRepository repository;

    public RatingRepositoryAdapter(RatingR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Rating> save(Rating rating) {
        return repository.save(rating);
    }

    @Override
    public Mono<Rating> findByUserIdAndTargetTypeAndTargetId(Long userId, String targetType,
            String targetId) {
        return repository.findByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);
    }

    @Override
    public Flux<Rating> findAllByTargetTypeAndTargetId(String targetType, String targetId) {
        return repository.findAllByTargetTypeAndTargetId(targetType, targetId);
    }

}
