package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Ranking;

@Repository
public interface RankingR2dbcRepository extends ReactiveCrudRepository<Ranking, Long> {
    Mono<Ranking> save(Ranking ranking);
    Flux<Ranking> findAllByUserIdAndTargetType(Long userId, String targetType);
    Flux<Ranking> findAllByTargetType(String targetType);
    Mono<Void> deleteById(Long id);
}
