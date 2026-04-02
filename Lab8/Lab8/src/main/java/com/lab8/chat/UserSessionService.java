package com.lab8.chat;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserSessionService {

    private final ConcurrentMap<String, String> sessionUsers = new ConcurrentHashMap<>();

    public void register(String sessionId, String username) {
        if (sessionId == null || username == null || username.isBlank()) {
            return;
        }
        sessionUsers.put(sessionId, username.trim());
    }

    public Optional<String> remove(String sessionId) {
        return Optional.ofNullable(sessionUsers.remove(sessionId));
    }

    public List<String> onlineUsers() {
        return sessionUsers.values().stream()
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .toList();
    }
}
