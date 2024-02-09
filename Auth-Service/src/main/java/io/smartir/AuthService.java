package io.smartir;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AuthService extends HelperFunctions {


    private final UserRepository userRepository;


    public AuthService( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new EmailIsAlreadyRegisteredException();
        if (!checkPasswordComplexity(request.getPassword())) throw new PasswordIsVerySimpleException();
        if (!request.getPassword().equals(request.getConfirmPassword())) throw new PasswordIsNotConfirmedException();
        var user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));
        userRepository.save(user);
    }
}
