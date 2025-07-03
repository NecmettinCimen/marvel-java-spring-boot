package xyz.necmettincimen.marvel.marvel.adapter.in.web;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import xyz.necmettincimen.marvel.marvel.application.service.UserService;
import xyz.necmettincimen.marvel.marvel.common.ApiResponse;
import xyz.necmettincimen.marvel.marvel.config.JwtUtil;
import xyz.necmettincimen.marvel.marvel.domain.dto.LoginResponse;
import xyz.necmettincimen.marvel.marvel.domain.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Mono<ApiResponse<List<User>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return userService.getAllUsers(page, pageSize)
                .collectList()
                .map(users -> new ApiResponse<List<User>>(users));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<User>> getUserById(@PathVariable Long id) {
        var emptyResult = new ApiResponse<User>(false, "User not found", null);
        return userService.getUserById(id).map(nullableUser -> {
            if (null == nullableUser) {
                return emptyResult;
            }
            return new ApiResponse<User>(nullableUser);
        }).defaultIfEmpty(emptyResult);
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .then(Mono.just(new ApiResponse<Void>(true, "User deleted successfully", null)))
                .onErrorResume(
                        e -> Mono.just(new ApiResponse<Void>(false, "Error deleting user: " + e.getMessage(), null)));
    }

    @PostMapping("/public/register")
    public Mono<ApiResponse<User>> registerUser(@RequestBody User registerRequest) {
        return userService.registerUser(registerRequest);
    }

    @PutMapping("/update")
    public Mono<ApiResponse<User>> updateUser(@RequestBody User updateRequest, ServerWebExchange exchange) {
        if (updateRequest.getId() == 0)
            updateRequest.setId(jwtUtil.extractId(exchange));
        return userService.updateUser(updateRequest);
    }

    @PostMapping("/public/login")
    public Mono<ApiResponse<LoginResponse>> login(@RequestBody User loginRequest) {
        ApiResponse<LoginResponse> errorResponse = new ApiResponse<LoginResponse>(false,
                "Username or password is incorrect", null);
        return userService.getUserByUsername(loginRequest.getUsername())
                .flatMap(user -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        String token = jwtUtil.generateToken(loginRequest.getUsername(), user.getId().toString());
                        return Mono.just(new ApiResponse<LoginResponse>(new LoginResponse(token, user)));
                    } else {
                        return Mono
                                .just(errorResponse);
                    }
                }).defaultIfEmpty(errorResponse);
    }

}
