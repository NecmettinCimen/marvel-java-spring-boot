package xyz.necmettincimen.marvel.marvel.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

public interface UserRepositoryPort {
    Flux<User> findAll(int page, int pageSize);
    Mono<User> findById(Long id);
    Mono<User> findByUsername(String username);
    Mono<ApiResponse<User>> save(User user);
    Mono<Void> deleteById(Long id);
    Mono<ApiResponse<User>> update(User updateRequest);
}
