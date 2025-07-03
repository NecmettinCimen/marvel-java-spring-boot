package xyz.necmettincimen.marvel.marvel.application.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;
import xyz.necmettincimen.marvel.marvel.domain.port.UserRepositoryPort;

@Service
public class UserService {
    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public Flux<User> getAllUsers(int page, int pageSize) {
        return userRepositoryPort.findAll(page, pageSize);
    }

    public Mono<User> getUserById(Long id) {
        return userRepositoryPort.findById(id);
    }

    public Mono<User> getUserByUsername(String username) {
        return userRepositoryPort.findByUsername(username);
    }
    public Mono<ApiResponse<User>> registerUser(User user){
        return userRepositoryPort.save(user);
    }
    public Mono<Void> deleteUser(Long id){
        return userRepositoryPort.deleteById(id);
    }

    public Mono<ApiResponse<User>> updateUser(User updateRequest) {
        return userRepositoryPort.update(updateRequest);
    }
}
