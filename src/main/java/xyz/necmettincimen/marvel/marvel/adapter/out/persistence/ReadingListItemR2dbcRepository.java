package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;

@Repository
public interface ReadingListItemR2dbcRepository extends ReactiveCrudRepository<ReadingListItem, Long> {
    Flux<ReadingListItem> findAllItemsByReadingListId(Long readingListId, int page, int pageSize);
    Mono<ReadingListItem> save(ReadingListItem readingListItem);
    Mono<Void> deleteItemById(Long id);
    Mono<Void> deleteByReadingListId(Long readingListId);
}