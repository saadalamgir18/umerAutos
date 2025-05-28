package com.example.umerautos.customresponse;

import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@ToString
public class CustomResponse {
    public static ResponseEntity<Object> generateResponse(HttpStatus httpStatus, boolean isSuccess, String message, Object responseBody){

        Map<String, Object> map = new HashMap<>();

         map.put("status", httpStatus.value());
         map.put("isSuccess", isSuccess);
         map.put("data", responseBody);
         map.put("message", message );
         return new ResponseEntity<>(map, httpStatus);
    }
    public static ResponseEntity<Object> generatePaginationResponse(HttpStatus httpStatus, boolean isSuccess, String message, Object responseBody, Object pagination){

        Map<String, Object> map = new HashMap<>();

        map.put("status", httpStatus.value());
        map.put("isSuccess", isSuccess);
        map.put("data", responseBody);
        map.put("pagination", pagination);
        map.put("message", message );
        return new ResponseEntity<>(map, httpStatus);
    }
}
