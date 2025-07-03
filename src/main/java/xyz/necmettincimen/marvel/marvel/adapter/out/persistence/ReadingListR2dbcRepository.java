package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;

@Repository
public interface ReadingListR2dbcRepository extends ReactiveCrudRepository<ReadingList, Long> {
    Flux<ReadingList> findAllByUserId(Long userId, int page, int pageSize);
    Mono<ReadingList> save(ReadingList readingList);
    Mono<Void> delete(ReadingList readingList);
}