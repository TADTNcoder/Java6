package com.lab7.controller;

import com.lab7.dto.AppUserRequest;
import com.lab7.entity.AppUser;
import com.lab7.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @GetMapping("/{username}")
    public AppUser findById(@PathVariable String username) {
        return appUserRepository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser create(@RequestBody AppUserRequest request) {
        validate(request.username(), request.password(), request.fullname(), request.enabled(), request.role());
        if (appUserRepository.existsById(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(request.username().trim());
        user.setPassword(request.password().trim());
        user.setFullname(request.fullname().trim());
        user.setEnabled(request.enabled());
        user.setRole(request.role());
        return appUserRepository.save(user);
    }

    @PutMapping("/{username}")
    public AppUser update(@PathVariable String username, @RequestBody AppUserRequest request) {
        validate(username, request.password(), request.fullname(), request.enabled(), request.role());
        AppUser user = appUserRepository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword(request.password().trim());
        user.setFullname(request.fullname().trim());
        user.setEnabled(request.enabled());
        user.setRole(request.role());
        return appUserRepository.save(user);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String username) {
        AppUser user = appUserRepository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        appUserRepository.delete(user);
    }

    private void validate(String username, String password, String fullname, Boolean enabled, Object role) {
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
        if (fullname == null || fullname.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fullname is required");
        }
        if (enabled == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enabled is required");
        }
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is required");
        }
    }
}
