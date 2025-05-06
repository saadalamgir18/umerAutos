package com.example.umerautos.globalException;

import com.example.umerautos.customresponse.CustomResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
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
