package xyz.necmettincimen.marvel.marvel.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public abstract class BaseModel {
    @Id
    private Long id;
    private LocalDateTime createdAt;
}
