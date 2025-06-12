package com.example.umerautos.services;

import com.example.umerautos.dto.UserSignupRequest;
import com.example.umerautos.dto.UserSignupResponse;
import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            return null;

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
}
