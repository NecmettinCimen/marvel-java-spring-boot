package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("reading_list_items")
public class ReadingListItem extends BaseModel {
    private Long readingListId;
    private String targetType;
    private String targetId;
}
