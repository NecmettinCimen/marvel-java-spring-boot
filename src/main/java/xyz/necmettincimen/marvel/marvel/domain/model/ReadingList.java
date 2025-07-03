package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("reading_lists")
public class ReadingList extends BaseModel {
    private Long userId;
    private String name;
    private String description;
    private boolean isPublic;
    private String coverImageUrl;

    public ReadingList() {
    }

    public ReadingList(Long userId, String name, String description, boolean isPublic, String coverImageUrl) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.coverImageUrl = coverImageUrl;
    }
}
