package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.RatingService;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.config.JwtUtil;
import xyz.necmettincimen.marvel.marvel.domain.model.Rating;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final JwtUtil jwtUtil;

    public RatingController(RatingService ratingService, JwtUtil jwtUtil) {
        this.ratingService = ratingService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Mono<ApiResponse<Rating>> rate(@RequestBody Rating rating, ServerWebExchange exchange) {
        rating.setUserId(jwtUtil.extractId(exchange));
        return ratingService.save(rating);
    }

    @GetMapping("/average")
    public Mono<ApiResponse<Double>> getAverage(@RequestParam String targetType, @RequestParam String targetId) {
        return ratingService.getAverageScore(targetType, targetId);
    }

}
