package com.example.umerautos.globalException;

public class ResourceAlreadyExistsException extends RuntimeException {
    String string;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }


}
