package com.lab8.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserSessionService userSessionService;

    public ChatController(SimpMessagingTemplate messagingTemplate, UserSessionService userSessionService) {
        this.messagingTemplate = messagingTemplate;
        this.userSessionService = userSessionService;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        if (message.getType() == null) {
            message.setType(ChatMessage.MessageType.CHAT);
        }
        return message;
    }

    @MessageMapping("/username")
    public void registerUsername(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        if (message == null || message.getSender() == null || message.getSender().isBlank()) {
            return;
        }
        String sessionId = headerAccessor.getSessionId();
        if (sessionId == null) {
            return;
        }

        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", message.getSender().trim());
        }
        userSessionService.register(sessionId, message.getSender().trim());
        publishUserList(ChatMessage.MessageType.JOIN);
    }

    public void handleDisconnect(String sessionId) {
        if (userSessionService.remove(sessionId).isPresent()) {
            publishUserList(ChatMessage.MessageType.LEAVE);
        }
    }

    private void publishUserList(ChatMessage.MessageType type) {
        List<String> users = userSessionService.onlineUsers();
        ChatMessage payload = new ChatMessage();
        payload.setType(type);
        payload.setSender("system");
        payload.setOnlineUsers(users);
        payload.setContent(String.join(", ", users));
        messagingTemplate.convertAndSend("/topic/users", payload);
    }
}
