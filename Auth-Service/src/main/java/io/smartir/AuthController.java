package io.smartir;

import io.smartir.entity.RoleEntity;
import io.smartir.model.Role;
import io.smartir.model.Token;
import io.smartir.model.User;
import io.smartir.request.RegisterRequest;
import io.smartir.request.UserRequest;
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
        var claims = authService.validateJWT(token);
        System.out.println(claims);
    }

    @GetMapping("get-user")
    public ResponseEntity<User> gertUser(@Param("token") String token) {

        return ResponseEntity.ok(User.toUser(authService.getUser(token)));

    }
}
