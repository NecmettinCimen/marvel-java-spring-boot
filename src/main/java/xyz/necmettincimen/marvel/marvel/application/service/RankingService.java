package xyz.necmettincimen.marvel.marvel.application.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.Ranking;
import xyz.necmettincimen.marvel.marvel.domain.port.RankingRepositoryPort;

@Service
public class RankingService {
    private final RankingRepositoryPort rankingRepositoryPort;

    public RankingService(RankingRepositoryPort rankingRepositoryPort) {
        this.rankingRepositoryPort = rankingRepositoryPort;
    }

    public Flux<Ranking> getUserRanking(Long userId, String targetType) {
        return rankingRepositoryPort.findAllByUserIdAndTargetType(userId, targetType);
    }

    public Flux<Ranking> getAllRankings(String targetType) {
        return rankingRepositoryPort.findAllByTargetType(targetType);
    }

    public Mono<Ranking> save(Ranking ranking) {
        return rankingRepositoryPort.save(ranking);
    }

    public Mono<Void> deleteRanking(Long id) {
        return rankingRepositoryPort.deleteById(id);
    }

}
