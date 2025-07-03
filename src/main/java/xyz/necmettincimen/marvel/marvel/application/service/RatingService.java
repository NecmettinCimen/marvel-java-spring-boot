package xyz.necmettincimen.marvel.marvel.application.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.Rating;
import xyz.necmettincimen.marvel.marvel.domain.port.RatingRepositoryPort;

@Service
public class RatingService {
    private final RatingRepositoryPort ratingRepositoryPort;

    public RatingService(RatingRepositoryPort ratingRepositoryPort) {
        this.ratingRepositoryPort = ratingRepositoryPort;
    }

    public Mono<ApiResponse<Rating>> save(Rating rating) {
        return ratingRepositoryPort.findByUserIdAndTargetTypeAndTargetId(rating.getUserId(), rating.getTargetType(), rating.getTargetId())
                .flatMap(existingRating -> {
                    existingRating.setScore(rating.getScore());
                    return ratingRepositoryPort.save(existingRating);
                })
                .switchIfEmpty(ratingRepositoryPort.save(rating))
                .map(savedRating -> new ApiResponse<Rating>(savedRating))
                .onErrorResume(e -> Mono.just(new ApiResponse<>(false, "Error saving rating: " + e.getMessage(), null)));
    }

    public Mono<ApiResponse<Double>> getAverageScore(String targetType, String targetId) {
        return ratingRepositoryPort.findAllByTargetTypeAndTargetId(targetType, targetId)
                .map(rating -> rating.getScore())
                .collectList()
                .map(scores -> new ApiResponse<>(scores.isEmpty() ? 0.0 : scores.stream().mapToInt(Integer::intValue).average().orElse(0.0)));
    }
}
