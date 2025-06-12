package com.example.umerautos.services;

import com.example.umerautos.dto.UserSignupRequest;
import com.example.umerautos.dto.UserSignupResponse;

public interface UserService {
    UserSignupResponse signup(UserSignupRequest user);

}
