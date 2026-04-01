package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable());

        http.authorizeHttpRequests(req -> req
                .requestMatchers("/login/**", "/access-denied").permitAll()
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

        http.oauth2Login(login -> {
            login.loginPage("/login/form");
            login.permitAll();

            login.successHandler((request, response, authentication) -> {
                DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
                String username = user.getEmail();

                UserDetails newUser = User.withUsername(username)
                        .password("{noop}")
                        .roles("OAUTH")
                        .build();

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        newUser, null, newUser.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(newAuth);

                HttpSession session = request.getSession();
                DefaultSavedRequest savedRequest =
                        (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                String redirectUrl = (savedRequest == null) ? "/" : savedRequest.getRedirectUrl();
                response.sendRedirect(redirectUrl);
            });
        });

        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/exit")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
        );

        return http.build();
    }
}