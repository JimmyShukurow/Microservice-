package io.smartir;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {

        authService.registerUser(request);
        return ResponseEntity.ok("User has been registered!");
    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody UserRequest request) {

        return ResponseEntity.ok(new User());
    }
}
