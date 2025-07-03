package xyz.necmettincimen.marvel.marvel.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Ranking;

public interface RankingRepositoryPort {
    Mono<Ranking> save(Ranking ranking);
    Flux<Ranking> findAllByUserIdAndTargetType(Long userId, String targetType);
    Flux<Ranking> findAllByTargetType(String targetType);
    Mono<Void> deleteById(Long id);
}
