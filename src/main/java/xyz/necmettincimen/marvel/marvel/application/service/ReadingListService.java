package xyz.necmettincimen.marvel.marvel.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.dto.ReadingListRequest;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;
import xyz.necmettincimen.marvel.marvel.domain.port.ReadingListRepositoryPort;

@Service
public class ReadingListService {
    private final ReadingListRepositoryPort readingListRepositoryPort;
    private final TransactionalOperator transactionalOperator;

    public ReadingListService(ReadingListRepositoryPort readingListRepositoryPort,
            TransactionalOperator transactionalOperator) {
        this.readingListRepositoryPort = readingListRepositoryPort;
        this.transactionalOperator = transactionalOperator;
    }

    public Flux<ReadingList> findAllByUserId(Long userId, int page, int pageSize) {
        return readingListRepositoryPort.findAllByUserId(userId, page, pageSize);
    }

    public Flux<ReadingListItem> findAllItemsByReadingListId(Long readingListId, int page, int pageSize) {
        return readingListRepositoryPort.findAllItemsByReadingListId(readingListId, page, pageSize);
    }

    public Mono<ReadingList> save(ReadingListRequest model) {
        return readingListRepositoryPort.save(model.getReadingList())
                .flatMap(savedList -> Flux.fromIterable(model.getItems())
                        .doOnNext(item -> item.setReadingListId(savedList.getId()))
                        .flatMap(readingListRepositoryPort::saveItem).then(Mono.just(savedList)))
                .as(transactionalOperator::transactional);
    }

    public Mono<Void> delete(Long id) {
        return readingListRepositoryPort.deleteById(id).flatMap(deletedList -> readingListRepositoryPort
                .deleteItemByReadingListId(id).as(transactionalOperator::transactional));
    }

    public Mono<Void> deleteItemById(Long id) {
        return readingListRepositoryPort.deleteItemById(id);
    }

}
