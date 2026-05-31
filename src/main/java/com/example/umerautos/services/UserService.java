package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    UserSignupResponse signup(UserSignupRequest user);

    LoginResponse login(UserLoginRequestDTO request);

    UserSignupResponse updateUser(UserUpdateRequest request, Long id);

    void deleteUser(Long id);

    List<UserSignupResponse> getAllUsers();

    UserSignupResponse getUserById(Long id);

    UserSignupResponse getUserByEmail(String email);

    LoginResponse refreshToken(HttpServletRequest request);
}
