package com.lab6.filter;


import com.lab6.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7).trim();

                try {
                    Claims claims = jwtService.getBody(token);

                    if (jwtService.validate(claims)) {
                        String username = claims.getSubject();
                        UserDetails user = userService.loadUserByUsername(username);

                        var auth = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                } catch (Exception e) {
                    // token sai hoặc hết hạn thì bỏ qua, request sẽ bị chặn ở Security
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}