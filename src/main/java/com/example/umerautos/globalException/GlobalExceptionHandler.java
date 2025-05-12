package com.example.umerautos.globalException;

import com.example.umerautos.customresponse.CustomResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
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
        return CustomResponse.generateResponse(HttpStatus.BAD_REQUEST, false, "Form input are missing", errors);

    }
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> notFoundException(){
        return CustomResponse.generateResponse(HttpStatus.NOT_FOUND, false, "Resource does not exist!", null);
    }

    @ExceptionHandler(value = RunTimeException.class)
    public ResponseEntity<?> runTimeException(){
        return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong!", null);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequestException(){
        return CustomResponse.generateResponse(HttpStatus.BAD_REQUEST, false, "Arguments are not correct!", null);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exception(){
        return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "unknown exception", null);
    }
}
