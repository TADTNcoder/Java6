package com.lab2.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
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
    public UserDetailsService userDetailsService(PasswordEncoder pe) {
        String pass = pe.encode("123");

        UserDetails user = User.withUsername("user@gmail.com")
                .password(pass)
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin@gmail.com")
                .password(pass)
                .roles("ADMIN")
                .build();

        UserDetails both = User.withUsername("both@gmail.com")
                .password(pass)
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin, both);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/poly/url1").authenticated()
                .requestMatchers("/poly/url2").hasRole("USER")
                .requestMatchers("/poly/url3").hasRole("ADMIN")
                .requestMatchers("/poly/url4").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login/form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/login/success", true)
                .failureUrl("/login/failure")
                .permitAll()
        );

        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/exit")
        );

        return http.build();
    }
}