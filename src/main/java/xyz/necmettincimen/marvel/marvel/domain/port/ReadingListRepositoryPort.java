package xyz.necmettincimen.marvel.marvel.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;

public interface ReadingListRepositoryPort {
    Flux<ReadingList> findAllByUserId(Long userId, int page, int pageSize);
    Flux<ReadingListItem> findAllItemsByReadingListId(Long readingListId, int page, int pageSize);
    Mono<ReadingList> save(ReadingList readingList);
    Mono<ReadingListItem> saveItem(ReadingListItem readingListItem);
    Mono<Void> deleteById(Long id);
    Mono<Void> deleteItemById(Long id);
    Mono<Void> deleteItemByReadingListId(Long readingListId);
}
