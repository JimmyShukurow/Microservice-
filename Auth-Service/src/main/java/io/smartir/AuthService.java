package io.smartir;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        var tokenEntity =tokenRepository.findByToken(token);
        if (tokenEntity.isEmpty() || tokenEntity.get().isExpired() || tokenEntity.get().isRevoked() ) throw new UserNotFoundException();
        return tokenEntity.get().getUser();
    }
}
