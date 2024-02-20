package io.smartir.ApiGateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyAdvice {
    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<String> wrongToken(WrongTokenException yourTokenExpiredException) {
        return new ResponseEntity<String>("Wrong Token!", HttpStatus.UNAUTHORIZED);
    }
}
