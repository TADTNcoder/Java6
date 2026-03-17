package com.lab1.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        return getAuth().getName();
    }

    public boolean isLogin() {
        return getAuth() != null && getAuth().isAuthenticated();
    }
}