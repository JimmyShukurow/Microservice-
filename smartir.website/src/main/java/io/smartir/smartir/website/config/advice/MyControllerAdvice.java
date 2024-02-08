package io.smartir.smartir.website.config.advice;


import io.smartir.smartir.website.exceptions.UnsupportedImageTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(UnsupportedImageTypeException.class)
    public ResponseEntity<String> handleUnsupportedImageType(UnsupportedImageTypeException unsupportedImageTypeException) {
        return new ResponseEntity<String>("Desteklenmeyen fotoğraf tipi!", HttpStatus.BAD_REQUEST);
    }
}
