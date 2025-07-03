
package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.RankingService;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.Ranking;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping
    public Mono<ApiResponse<Ranking>> save(@RequestBody Ranking ranking) {
        return rankingService.save(ranking)
                .map(f -> new ApiResponse<>(f))
                .onErrorResume(
                        e -> Mono.just(new ApiResponse<>(false, "Error saving ranking: " + e.getMessage(), null)));
    }

    @GetMapping("/user")
    public Flux<ApiResponse<Ranking>> getUserRankings(@RequestParam Long userId, @RequestParam String targetType) {
        return rankingService.getUserRanking(userId, targetType)
                .map(ranking -> new ApiResponse<>(ranking))
                .onErrorResume(e -> Flux
                        .just(new ApiResponse<>(false, "Error fetching user rankings: " + e.getMessage(), null)));
    }

    @GetMapping("/auto")
    public Flux<ApiResponse<Ranking>> getAutoRankings(@RequestParam String targetType) {
        return rankingService.getAllRankings(targetType)
                .map(f -> new ApiResponse<>(f))
                .onErrorResume(e -> Flux
                        .just(new ApiResponse<>(false, "Error fetching auto rankings: " + e.getMessage(), null)));
    }

    @DeleteMapping
    public Mono<ApiResponse<Void>> delete(@RequestParam Long id) {
        return rankingService.deleteRanking(id)
                .then(Mono.just(new ApiResponse<Void>(true, "Ranking deleted successfully", null)))
                .onErrorResume(e -> Mono
                        .just(new ApiResponse<Void>(false, "Error deleting ranking: " + e.getMessage(), null)));
    }

}