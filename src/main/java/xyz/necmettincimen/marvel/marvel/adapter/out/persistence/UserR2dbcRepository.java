package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

@Repository
public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
