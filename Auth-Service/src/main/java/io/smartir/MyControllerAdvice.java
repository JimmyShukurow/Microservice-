package io.smartir;

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
}
