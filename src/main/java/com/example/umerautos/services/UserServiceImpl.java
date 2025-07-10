package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.globalException.ResourceAlreadyExistsException;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;


    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailsService;


    @Override
    public UserSignupResponse signup(UserSignupRequest user) {

        Optional<SalesPerson> dbUser = userRepository.findSalesPersonByEmail(user.email());
        if (dbUser.isPresent()) {
            throw new ResourceAlreadyExistsException("user already exists with email: " + user.email());
        }

        String hashedPassword = passwordEncoder.encode(user.password());
        System.out.println("hashedPassword: " + hashedPassword);

        SalesPerson newUser = userRepository.save(SalesPerson
                .builder()
                .email(user.email())
                .userName(user.userName())
                .password(hashedPassword)
                .build());

        return UserSignupResponse
                .builder()
                .email(newUser.getEmail())
                .userName(newUser.getUserName())
                .role(newUser.getRole())
                .build();

    }

    @Override
    public LoginResponse login(UserLoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();


            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            System.out.println("accessToken: " + accessToken);
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return LoginResponse.builder()
                .build();
    }

    @Override
    public UserSignupResponse updateUser(UserUpdateRequest request, UUID id) {

        SalesPerson dbUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));


        dbUser.setRole(Set.of(request.roles()));

        SalesPerson updateUser = userRepository.save(dbUser);


        return UserSignupResponse
                .builder()
                .email(updateUser.getEmail())
                .userName(updateUser.getUserName())
                .role(updateUser.getRole())
                .build();
    }

    @Override
    public void deleteUser(UUID id) {
        try {
            SalesPerson dbUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));


            userRepository.delete(dbUser);

        } catch (RuntimeException e) {
            throw new RunTimeException();

        }


    }

    @Override
    public List<UserSignupResponse> getAllUsers() {
        try {
            List<SalesPerson> allUsers = userRepository.findAll();
            return allUsers.stream().map(user -> UserSignupResponse.builder()
                    .email(user.getEmail())
                    .userName(user.getUserName())
                    .role(user.getRole())
                    .id(user.getId())
                    .build()).toList();
        } catch (Exception e) {

            throw new RunTimeException();

        }
    }

    @Override
    public UserSignupResponse getUserById(UUID id) {
        SalesPerson user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));

        return UserSignupResponse
                .builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }

    @Override
    public LoginResponse refreshToken(HttpServletRequest request) {

        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("No refresh token found"));

        String email = jwtService.extractEmail(refreshToken);
        UserDetails user = userDetailsService.loadUserByUsername(email);

        String accessToken = jwtService.generateAccessToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
}
