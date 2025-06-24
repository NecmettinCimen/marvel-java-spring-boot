package xyz.necmettincimen.marvel.marvel.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

public interface UserRepositoryPort {
    Flux<User> findAll();
    Mono<User> findById(Long id);
    Mono<User> findByUsername(String username);
    Mono<User> save(User user);
    Mono<Void> deleteById(Long id);
}
