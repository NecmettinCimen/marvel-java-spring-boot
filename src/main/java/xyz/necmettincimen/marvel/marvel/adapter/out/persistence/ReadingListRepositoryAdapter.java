package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;
import xyz.necmettincimen.marvel.marvel.domain.port.ReadingListRepositoryPort;

@Component
public class ReadingListRepositoryAdapter implements ReadingListRepositoryPort {
    
    private final ReadingListR2dbcRepository readingListR2dbcRepository;
    private final ReadingListItemR2dbcRepository readingListItemR2dbcRepository;

    public ReadingListRepositoryAdapter(ReadingListR2dbcRepository readingListR2dbcRepository, ReadingListItemR2dbcRepository readingListItemR2dbcRepository) {
        this.readingListR2dbcRepository = readingListR2dbcRepository;
        this.readingListItemR2dbcRepository = readingListItemR2dbcRepository;
    }

    @Override
    public Flux<ReadingList> findAllByUserId(Long userId, int page, int pageSize) {
        return readingListR2dbcRepository.findAllByUserId(userId, page, pageSize);
    }

    @Override
    public Flux<ReadingListItem> findAllItemsByReadingListId(Long readingListId, int page, int pageSize) {
        return readingListItemR2dbcRepository.findAllItemsByReadingListId(readingListId, page, pageSize);
    }

    @Override
    public Mono<ReadingList> save(ReadingList readingList) {
        return readingListR2dbcRepository.save(readingList);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return readingListR2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteItemById(Long id) {
        return readingListItemR2dbcRepository.deleteItemById(id);
    }

    @Override
    public Mono<Void> deleteItemByReadingListId(Long readingListId) {
        return readingListItemR2dbcRepository.deleteByReadingListId(readingListId);
    }

    @Override
    public Mono<ReadingListItem> saveItem(ReadingListItem readingListItem) {    
        return readingListItemR2dbcRepository.save(readingListItem);
    }
    
}
