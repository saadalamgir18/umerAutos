package com.example.umerautos.services;

import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        SalesPerson user = userRepository.findSalesPersonByEmail(username).orElseThrow(() -> {
            logger.error("User not found with username or email: {}", username);
            return new UsernameNotFoundException("User not found with username or email: " + username);
        });

        Set<GrantedAuthority> authorities = user
                .getRole()
                .stream()
                .map(roles -> new SimpleGrantedAuthority(roles.name()))
                .collect(Collectors.toSet());

        logger.info("User loaded successfully: {}", username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);

    }
}
