package com.example.umerautos.services;

import com.example.umerautos.dto.LoginResponse;
import com.example.umerautos.dto.UserLoginRequestDTO;

public interface AuthService {

    LoginResponse login(UserLoginRequestDTO request);


}
