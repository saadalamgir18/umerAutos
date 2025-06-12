package com.example.umerautos.services;

import com.example.umerautos.configsecurity.MyUserDetails;
import com.example.umerautos.entities.SalesPerson;
import com.example.umerautos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SalesPerson> user = userRepository.findSalesPersonByEmail(username);
        if (user.isPresent()) {
            return new MyUserDetails(user.get());

        } else {
            throw new UsernameNotFoundException("Cannot find the user by the give email!");

        }
    }
}
