package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserSignupResponse signup(UserSignupRequest user);

    LoginResponse login(UserLoginRequestDTO request);


    UserSignupResponse updateUser(UserUpdateRequest request, UUID id);

    void deleteUser(UUID id);

    List<UserSignupResponse> getAllUsers();

    UserSignupResponse getUserById(UUID id);


    LoginResponse refreshToken(HttpServletRequest request);
}
