package com.example.demo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login/**").permitAll()
                .requestMatchers("/poly/url1").authenticated()
                .requestMatchers("/poly/url2").hasRole("USER")
                .requestMatchers("/poly/url3").hasRole("ADMIN")
                .requestMatchers("/poly/url4").hasAnyRole("USER", "ADMIN")
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

        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
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