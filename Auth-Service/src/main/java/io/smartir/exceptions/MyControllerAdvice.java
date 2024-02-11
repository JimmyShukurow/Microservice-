package io.smartir.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(PasswordIsNotConfirmedException.class)
    public ResponseEntity<String> confirmationFailed(PasswordIsNotConfirmedException passwordIsNotConfirmedException) {
        return new ResponseEntity<String>("Password and Confirmation are not equals!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordIsVerySimpleException.class)
    public ResponseEntity<String> simplePassword(PasswordIsVerySimpleException passwordIsVerySimpleException) {
        return new ResponseEntity<String>("Password is very simple!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailIsAlreadyRegisteredException.class)
    public ResponseEntity<String> emailIsTaken(EmailIsAlreadyRegisteredException emailIsTaken) {
        return new ResponseEntity<String>("Email is already registered!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFound(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<String>("There is no user with this ID!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFounded.class)
    public ResponseEntity<String> roleNotFound(RoleNotFounded roleNotFounded) {
        return new ResponseEntity<String>("There is no role like this!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyHasThisRole.class)
    public ResponseEntity<String> roleAlreadyAssigned(UserAlreadyHasThisRole userAlreadyHasThisRole) {
        return new ResponseEntity<String>("User already has this role!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordIsIncorrectException.class)
    public ResponseEntity<String> wrongPassword(PasswordIsIncorrectException passwordIsIncorrectException) {
        return new ResponseEntity<String>("Wrong Credentials!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(YourTokenExpiredException.class)
    public ResponseEntity<String> tokenExpired(YourTokenExpiredException yourTokenExpiredException) {
        return new ResponseEntity<String>("Your session is expired!", HttpStatus.UNAUTHORIZED);
    }
}
