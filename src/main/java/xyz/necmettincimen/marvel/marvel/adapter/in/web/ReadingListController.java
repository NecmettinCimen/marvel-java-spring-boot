package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.ReadingListService;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.dto.ReadingListRequest;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;

@RestController
@RequestMapping("/api/readingLists")
public class ReadingListController {
    private final ReadingListService readingListService;

    public ReadingListController(ReadingListService readingListService) {
        this.readingListService = readingListService;
    }

    @GetMapping("/{userId}")
    Mono<ApiResponse<List<ReadingList>>> findAllByUserId(Long userId, @RequestParam int page, @RequestParam int pageSize) {
        return readingListService.findAllByUserId(userId, page, pageSize).collectList().map(m -> new ApiResponse<>(m));
    }

    @GetMapping("/items/{readingListId}")
    Mono<ApiResponse<List<ReadingListItem>>> findAllItemsByReadingListId(Long readingListId, int page, int pageSize) {
        return readingListService.findAllItemsByReadingListId(readingListId, page, pageSize).collectList()
                .map(m -> new ApiResponse<>(m));
    }

    @PostMapping
    Mono<ApiResponse<ReadingList>> save(@RequestBody ReadingListRequest model) {
        return readingListService.save(model)
                .map(m -> new ApiResponse<>(m));
    }

    @DeleteMapping("/delete")
    Mono<ApiResponse<Void>> delete(@RequestParam Long id) {
        return readingListService.delete(id)
                .map(m -> new ApiResponse<>(true, "Deleted", null));
    }

    @DeleteMapping("/deleteItemById")
    Mono<ApiResponse<Void>> deleteItemById(@RequestParam Long id) {
        return readingListService.deleteItemById(id)
                .map(m -> new ApiResponse<>(true, "Deleted Item", null));
    }

}
