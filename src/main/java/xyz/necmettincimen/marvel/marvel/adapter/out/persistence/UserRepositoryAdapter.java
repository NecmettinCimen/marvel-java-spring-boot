package xyz.necmettincimen.marvel.marvel.adapter.out.persistence;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;
import xyz.necmettincimen.marvel.marvel.domain.port.UserRepositoryPort;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserR2dbcRepository userR2dbcRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserRepositoryAdapter(UserR2dbcRepository userR2dbcRepository) {
        this.userR2dbcRepository = userR2dbcRepository;
    }

    @Override
    public Flux<User> findAll(int page, int pageSize) {
        return userR2dbcRepository.findAllByOrderByIdDesc()
                .skip(page * pageSize)
                .take(pageSize);
    }

    @Override
    public Mono<User> findById(Long id) {
        return userR2dbcRepository.findById(id);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userR2dbcRepository.findByUsername(username);
    }

    @Override
    public Mono<ApiResponse<User>> save(User user) {
        return findByUsername(user.getUsername())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(new ApiResponse<User>(false, "Username already exists", null));
                    }
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return userR2dbcRepository.save(user)
                            .map(savedUser -> new ApiResponse<User>(true, "User saved successfully", savedUser));
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<ApiResponse<User>> update(User updateRequest) {
        return findById(updateRequest.getId())
                .flatMap(existingUser -> {
                    if (existingUser == null) {
                        return Mono.just(new ApiResponse<User>(false, "User not found", null));
                    }
                    existingUser.setUsername(updateRequest.getUsername());
                    existingUser.setEmail(updateRequest.getEmail());
                    if(updateRequest.getPassword() != null || existingUser.getPassword() != updateRequest.getPassword()) 
                    existingUser.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
                    return userR2dbcRepository.save(existingUser)
                            .map(savedUser -> new ApiResponse<User>(true, "User updated successfully", savedUser));
                });
    }

}
