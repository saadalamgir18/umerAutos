package com.example.umerautos.configsecurity;

import com.example.umerautos.entities.SalesPerson;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails extends SalesPerson implements UserDetails {

    private String email;
    private String userPassword;
    private String role;

    public MyUserDetails(SalesPerson user) {

        this.email = user.getEmail();
        this.userPassword = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
