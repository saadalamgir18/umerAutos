package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.Roles;
import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.globalException.ResourceAlreadyExistsException;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserSignupResponse signup(UserSignupRequest user) {
        logger.info("Attempting to signup user: {}", user.email());
        Optional<SalesPerson> dbUser = userRepository.findSalesPersonByEmail(user.email());
        if (dbUser.isPresent()) {
            logger.warn("User already exists with email: {}", user.email());
            throw new ResourceAlreadyExistsException("user already exists with email: " + user.email());
        }

        String hashedPassword = passwordEncoder.encode(user.password());

        SalesPerson newUser = userRepository.save(SalesPerson
                .builder()
                .email(user.email())
                .userName(user.userName())
                .password(hashedPassword)
                .role(Set.of(Roles.ROLE_USER))
                .build());

        logger.info("User signed up successfully with id: {}", newUser.getId());
        return UserSignupResponse
                .builder()
                .email(newUser.getEmail())
                .userName(newUser.getUserName())
                .roles(newUser.getRole())
                .id(newUser.getId())
                .build();

    }

    @Override
    public LoginResponse login(UserLoginRequestDTO request) {
        logger.info("Attempting login for user: {}", request.email());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            logger.info("User authenticated successfully: {}", request.email());

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        logger.warn("Authentication failed for user: {}", request.email());
        return LoginResponse.builder()
                .build();
    }

    @Override
    public UserSignupResponse updateUser(UserUpdateRequest request, Long id) {
        logger.info("Updating user with id: {}", id);
        SalesPerson dbUser = userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new ResourceNotFoundException("user not found with id: " + id);
        });


        dbUser.setRole(new HashSet<>(Collections.singleton(request.roles())));

        SalesPerson updateUser = userRepository.save(dbUser);
        logger.info("User updated successfully");

        return UserSignupResponse
                .builder()
                .email(updateUser.getEmail())
                .userName(updateUser.getUserName())
                .roles(updateUser.getRole())
                .id(updateUser.getId())
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        try {
            SalesPerson dbUser = userRepository.findById(id).orElseThrow(() -> {
                logger.error("User not found with id: {}", id);
                return new ResourceNotFoundException("user not found with id: " + id);
            });


            userRepository.delete(dbUser);
            logger.info("User deleted successfully");

        } catch (RuntimeException e) {
            logger.error("Error deleting user with id: {}", id, e);
            throw new RunTimeException();

        }


    }

    @Override
    public List<UserSignupResponse> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<SalesPerson> allUsers = userRepository.findAll();
            logger.info("Found {} users", allUsers.size());
            return allUsers.stream().map(user -> UserSignupResponse.builder()
                    .email(user.getEmail())
                    .userName(user.getUserName())
                    .roles(user.getRole())
                    .id(user.getId())
                    .build()).toList();
        } catch (Exception e) {
            logger.error("Error fetching all users", e);
            throw new RunTimeException();

        }
    }

    @Override
    public UserSignupResponse getUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        SalesPerson user = userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new ResourceNotFoundException("user not found with id: " + id);
        });

        return UserSignupResponse
                .builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .roles(user.getRole())
                .id(user.getId())
                .build();
    }

    @Override
    public UserSignupResponse getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        SalesPerson user = userRepository.findSalesPersonByEmail(email).orElseThrow(() -> {
            logger.error("User not found with email: {}", email);
            return new ResourceNotFoundException("user not found with email: " + email);
        });

        return UserSignupResponse
                .builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .roles(user.getRole())
                .id(user.getId())
                .build();
    }

    @Override
    public LoginResponse refreshToken(HttpServletRequest request) {
        logger.info("Refreshing token");
        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> {
                    logger.error("No refresh token found in cookies");
                    return new AuthenticationServiceException("No refresh token found");
                });

        String email = jwtService.extractEmail(refreshToken);
        UserDetails user = userDetailsService.loadUserByUsername(email);

        String accessToken = jwtService.generateAccessToken(user);
        logger.info("Token refreshed successfully for user: {}", email);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
}
