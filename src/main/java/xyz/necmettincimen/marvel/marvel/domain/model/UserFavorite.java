package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("user_favorites")
public class UserFavorite extends BaseModel {
    private Long userId;
    private String favoriteType;
    private String favoriteId;

    public UserFavorite() {
    }

    public UserFavorite(Long userId, String favoriteType, String favoriteId) {
        this.userId = userId;
        this.favoriteType = favoriteType;
        this.favoriteId = favoriteId;
    }
}
