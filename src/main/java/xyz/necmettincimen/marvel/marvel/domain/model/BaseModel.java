package xyz.necmettincimen.marvel.marvel.domain.model;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class BaseModel {
    @Id
    private Long id;
    private Date createdAt;
}
