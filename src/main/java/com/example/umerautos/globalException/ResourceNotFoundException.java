package com.example.umerautos.globalException;

import lombok.NoArgsConstructor;

public class ResourceNotFoundException extends RuntimeException {
    String string;
    public ResourceNotFoundException(String message){
        super(message);
    }

}
