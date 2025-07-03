package xyz.necmettincimen.marvel.marvel.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("users")
public class User extends BaseModel {
    private String username;
    private String password;
    private String email;

    public User() {
    }
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
