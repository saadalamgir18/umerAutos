package com.example.umerautos.globalException;

import com.example.umerautos.dto.ExceptionResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        System.out.println("exception occurred");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println("sending error in response");
        return new ResponseEntity<>("Form input are missing" ,HttpStatus.BAD_REQUEST);

//        return CustomResponse.generateResponse(HttpStatus.BAD_REQUEST, false, "Form input are missing", errors);

    }
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){


        String message = exception.getMessage();
        return new ResponseEntity<>(new ExceptionResponse("resource not found", false),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = RunTimeException.class)
    public ResponseEntity<?> runTimeException(){
        return new ResponseEntity<>("something went wrong!" ,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequestException(){
        return new ResponseEntity<>("Arguments are not correct!" ,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ResponseEntity<>("Endpoint not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exception(){
        return new ResponseEntity<>("unknown exception" ,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
