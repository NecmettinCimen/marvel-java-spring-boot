package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    public User() {
        super();
    }
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
