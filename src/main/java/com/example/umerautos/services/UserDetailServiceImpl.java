package com.example.umerautos.services;

import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SalesPerson user = userRepository.findSalesPersonByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email: " + username));

        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_"+user.getRole())
        );

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);

    }
}
