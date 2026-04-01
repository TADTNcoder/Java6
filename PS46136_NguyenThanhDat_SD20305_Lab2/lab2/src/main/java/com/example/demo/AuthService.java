package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("auth")
public class AuthService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        return getAuthentication().getName();
    }

    public List<String> getRoles() {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
    }

    public boolean isAuthenticated() {
        return getAuthentication() != null
                && getAuthentication().isAuthenticated()
                && !"anonymousUser".equals(getUsername());
    }

    public boolean hasAnyRole(String... roles) {
        List<String> authorities = getRoles();
        for (String role : roles) {
            if (authorities.contains("ROLE_" + role)) {
                return true;
            }
        }
        return false;
    }
}