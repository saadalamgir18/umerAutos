package com.example.umerautos.globalException;

import com.example.umerautos.dto.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<?> resourceAlreadyExist(ResourceAlreadyExistsException exception) {


        String message = exception.getMessage();
        return new ResponseEntity<>(new ExceptionResponse(message, false), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(ResourceAlreadyExistsException exception) {


        String message = exception.getMessage();
        return new ResponseEntity<>(new ExceptionResponse(message, false), HttpStatus.FORBIDDEN);

    }


    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {


        String message = exception.getMessage();
        return new ResponseEntity<>(new ExceptionResponse(message, false), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = RunTimeException.class)
    public ResponseEntity<?> runTimeException(Exception exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(Exception exception) {
        return new ResponseEntity<>(new ExceptionResponse("username or password incorrect!", false), HttpStatus.FORBIDDEN);

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

        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
