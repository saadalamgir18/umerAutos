package com.example.umerautos.services;

import com.example.umerautos.dto.UserSignupRequest;
import com.example.umerautos.dto.UserSignupResponse;
import com.example.umerautos.dto.UserUpdateRequest;
import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.globalException.ResourceAlreadyExistsException;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
    public UserSignupResponse updateUser(UserUpdateRequest request, UUID id) {

        SalesPerson dbUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));


        dbUser.setRole(request.roles().toString());

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
}
