package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("rankings")
public class Ranking extends BaseModel {
    private Long userId;
    private String targetType;
    private String targetId;
    private Integer rankIndex;
}
