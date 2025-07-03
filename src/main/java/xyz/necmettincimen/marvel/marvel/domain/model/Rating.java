package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("ratings")
public class Rating extends BaseModel {
    private Long userId;
    private String targetType;
    private String targetId;
    private int score;

    public Rating() {
    }

    public Rating(Long userId, String targetType, String targetId, int score) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.score = score;
    }
}
