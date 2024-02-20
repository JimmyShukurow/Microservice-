package io.smartir;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.smartir.entity.RoleEntity;
import io.smartir.exceptions.YourTokenExpiredException;
import io.smartir.model.Role;
import io.smartir.model.Token;
import io.smartir.model.User;
import io.smartir.request.RegisterRequest;
import io.smartir.request.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {

        authService.registerUser(request);
        return ResponseEntity.ok("User has been registered!");
    }

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        authService.logout(request);
        return ResponseEntity.ok("You have been logged out.");
    }

    @PutMapping("{id}/assign-role/{role}")
    public ResponseEntity<String> assignNewRoleToUser(@PathVariable String id, @PathVariable String role) {
        authService.assignNewRoleToUser(id, role);
        return ResponseEntity.ok("User Roles are updated!");
    }

    @GetMapping("get-all-roles")
    public ResponseEntity<List<Role>> getAllRoles() {

        var roles = authService.getAllRoles();
        return ResponseEntity.ok(roles.stream().map(Role::toRole).collect(Collectors.toList()));
    }

    @GetMapping("get-all-role-names")
    public ResponseEntity<List<String>> getAllRoleNames() {

        var roles = authService.getAllRoles();
        return ResponseEntity.ok(roles.stream().map(RoleEntity::getName).collect(Collectors.toList()));
    }

    @PostMapping("validate-JWT")
    public void validateToken(@Param("token") String token) {
        try {
            var claims = authService.validateJWT(token);
        } catch (ExpiredJwtException e) {
            throw new YourTokenExpiredException();
        }
    }

    @GetMapping("get-user")
    public ResponseEntity<User> gertUser(@Param("token") String token) {
        var user = authService.getUser(token);
        return ResponseEntity.ok(User.toUser(user));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<User>> getAllUsers() {
        var users = authService.getAllUsers();
        return ResponseEntity.ok().body(users.stream().map(User::toUser).toList());
    }
}
