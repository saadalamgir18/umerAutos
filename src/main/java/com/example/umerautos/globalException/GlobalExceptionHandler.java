package com.example.umerautos.globalException;

import com.example.umerautos.dto.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        System.out.println("validation exception occurred");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {


        String message = exception.getMessage();
        return new ResponseEntity<>(new ExceptionResponse(message, false), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = RunTimeException.class)
    public ResponseEntity<?> runTimeException(Exception exception) {
        System.out.println("run time exception");
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(Exception exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequestException() {
        return new ResponseEntity<>("Arguments are not correct!", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<?> malformedJwtException(MalformedJwtException exception) {

        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtException(ExpiredJwtException exception) {

        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException exception) {

        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResponseEntity<?> unsupportedJwtException(UnsupportedJwtException exception) {

        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exception(Exception exception) {
        System.out.println("generel exception occurred");

        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
