package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.UserService;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public Flux<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping("/register")
    public Mono<User> registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }
    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

}
