package com.example.umerautos.services;

import com.example.umerautos.dto.UserSignupRequest;
import com.example.umerautos.dto.UserSignupResponse;
import com.example.umerautos.dto.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserSignupResponse signup(UserSignupRequest user);

    UserSignupResponse updateUser(UserUpdateRequest request, UUID id);

    void deleteUser(UUID id);

    List<UserSignupResponse> getAllUsers();

    UserSignupResponse getUserById(UUID id);
}
