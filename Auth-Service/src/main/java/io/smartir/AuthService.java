package io.smartir;

import io.jsonwebtoken.ExpiredJwtException;
import io.smartir.entity.RoleEntity;
import io.smartir.entity.TokenEntity;
import io.smartir.entity.TokenTypes;
import io.smartir.entity.UserEntity;
import io.smartir.exceptions.*;
import io.smartir.helper.HelperFunctions;
import io.smartir.model.Token;
import io.smartir.repository.RoleRepository;
import io.smartir.repository.TokenRepository;
import io.smartir.repository.UserRepository;
import io.smartir.request.RegisterRequest;
import io.smartir.request.UserRequest;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AuthService extends HelperFunctions {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;




    public AuthService(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    public void registerUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new EmailIsAlreadyRegisteredException();
        if (!checkPasswordComplexity(request.getPassword())) throw new PasswordIsVerySimpleException();
        if (!request.getPassword().equals(request.getConfirmPassword())) throw new PasswordIsNotConfirmedException();
        var user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));
        var userRole = roleRepository.findByName("USER");
        log.info(userRole.get().toString());
        user.setRoles(Set.of(userRole.get()));
        userRepository.save(user);
    }

    public void assignNewRoleToUser(String id, String role) {

//        checkUserPermissionToDoAction(id, request);

        var user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty()) throw new UserNotFoundException();
        var userEntity = user.get();
        var targetRole = roleRepository.findByName(role);
        if (targetRole.isEmpty()) throw new RoleNotFounded();
        if (checkIfUserHasRole(userEntity, targetRole.get())) throw new UserAlreadyHasThisRole();
        userEntity.addRole(targetRole.get());
        userRepository.save(userEntity);
    }

    public boolean checkIfUserHasRole(UserEntity userEntity, RoleEntity roleEntity) {
        return userEntity.getRoles().stream().anyMatch(role -> role.equals(roleEntity));
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public Token login(UserRequest request) {

        var user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) throw new UserNotFoundException();
        if (!user.get().getPassword().equals(hashPassword(request.getPassword()))) {
            throw new PasswordIsIncorrectException();
        }
        var token = generateJWToken(user.get().getName(), user.get().getEmail(), user.get().getRoles().stream().map(RoleEntity::getName).toList().toString());
        var tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setTokenType(TokenTypes.BEARER);
        tokenEntity.setUser(user.get());
        tokenRepository.save(tokenEntity);
        return Token.toToken(tokenEntity);
    }

    public UserEntity getUser(String token) {
        try {
            validateJWT(token);
        } catch (ExpiredJwtException e) {
            tokenRepository.findByToken(token).get().setExpired(true);
            throw new YourTokenExpiredException();
        } catch (Exception e) {
            throw new WrongTokenException();
        }

        var tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity.isEmpty() || tokenEntity.get().isExpired() || tokenEntity.get().isRevoked())
            throw new UserNotFoundException();
        var user = tokenEntity.get().getUser();
        return user;
    }

    public void checkUserPermissionToDoAction(String id, HttpServletRequest request) {
        var header = request.getHeader("Authorization");

        var tokenEntity = tokenRepository.findByToken(header.split(" ")[1]);
        if (!(tokenEntity.get().getUser().getId() == Long.parseLong(id))) throw new UserHasNotPermission();
    }

    public void logout(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        var token = tokenRepository.findByToken(header.split(" ")[1]);
        if (token.isEmpty()) throw new WrongTokenException();
        var entity = token.get();
        entity.setRevoked(true);
        tokenRepository.save(entity);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
