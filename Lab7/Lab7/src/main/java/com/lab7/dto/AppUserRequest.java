package com.lab7.dto;

import com.lab7.entity.Role;

public record AppUserRequest(
        String username,
        String password,
        String fullname,
        Boolean enabled,
        Role role
) {
}
