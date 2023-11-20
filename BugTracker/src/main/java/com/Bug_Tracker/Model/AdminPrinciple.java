
package com.Bug_Tracker.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class AdminPrinciple implements UserDetails {
    private Admin admin;
    public AdminPrinciple(Admin admin){this.admin=admin;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(this.admin.getAuthorities())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }
    @Override
    public String getPassword() {
        return this.admin.getPassword();
    }

    @Override
    public String getUsername() {
        return this.admin.getUsername();

    }

    @Override
    public boolean isAccountNonExpired() {
        return true; }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

