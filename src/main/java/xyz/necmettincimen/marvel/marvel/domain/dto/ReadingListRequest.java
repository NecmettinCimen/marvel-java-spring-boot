package xyz.necmettincimen.marvel.marvel.domain.dto;

import java.util.List;

import lombok.Data;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingList;
import xyz.necmettincimen.marvel.marvel.domain.model.ReadingListItem;

@Data
public class ReadingListRequest {
    ReadingList readingList;
    List<ReadingListItem> items;

    public ReadingListRequest() {
    }

    public ReadingListRequest(ReadingList readingList, List<ReadingListItem> items) {
        this.readingList = readingList;
        this.items = items;
    }
}
