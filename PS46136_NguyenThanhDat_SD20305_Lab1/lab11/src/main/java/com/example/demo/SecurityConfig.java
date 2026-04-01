package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode("123");

        UserDetails user = User.withUsername("user@gmail.com")
                .password(password)
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin@gmail.com")
                .password(password)
                .roles("ADMIN")
                .build();

        UserDetails both = User.withUsername("both@gmail.com")
                .password(password)
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin, both);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login/**").permitAll()
                .requestMatchers("/poly/**").authenticated()
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login/form")
                .loginProcessingUrl("/login/check")
                .defaultSuccessUrl("/login/success", true)
                .failureUrl("/login/failure")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
        );

        http.rememberMe(remember -> remember
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me")
                .tokenValiditySeconds(3 * 24 * 60 * 60)
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/exit")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", "remember-me")
        );

        return http.build();
    }
}