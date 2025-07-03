package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Ranking;
import xyz.necmettincimen.marvel.marvel.domain.port.RankingRepositoryPort;

@Component
public class RankingR2dbcRepositoryAdapter implements RankingRepositoryPort {
    private final RankingR2dbcRepository repository;
    public RankingR2dbcRepositoryAdapter(RankingR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Ranking> save(Ranking ranking) {
        return repository.save(ranking);
    }

    @Override
    public Flux<Ranking> findAllByUserIdAndTargetType(Long userId, String targetType) {
        return repository.findAllByUserIdAndTargetType(userId, targetType);
    }

    @Override
    public Flux<Ranking> findAllByTargetType(String targetType) {
        return repository.findAllByTargetType(targetType);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
