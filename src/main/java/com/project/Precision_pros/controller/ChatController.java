package com.project.Precision_pros.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate; // Import SimpMessagingTemplate
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.payload.request.ChatMessageRequest;
import com.project.Precision_pros.payload.response.ChatMessageResponse;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.MessageService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Inject SimpMessagingTemplate
    @Autowired
    JwtUtils jwtUtil;
    // This method is for handling REST POST requests to /api/room
    @PostMapping("/room")
    public ChatMessageResponse sendMessage(@RequestBody ChatMessageRequest request) {
        logger.info("Received REST message request for roomId: {}, userId: {}, content: {}",
                request.getRoomId(), request.getUserId(), request.getContent());
        ChatMessageResponse response = messageService.saveMessage(request);
        logger.info("REST message saved successfully. Username: {}, Content: {}",
                response.getUsername(), response.getContent());
        return response;
    }
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<List<ChatMessageResponse>> getMessagesByRoom(
            @PathVariable Long roomId) {
        // Assuming JwtUtils is accessible or injected to extract username
        // For simplicity, I'm directly passing the username here.
        // You might need to adjust this to extract username from JWT in a more robust way.
        // Implement this method based on your JwtUtils
        return ResponseEntity.ok(messageService.getMessagesByRoom(roomId));
    }
    // Corrected WebSocket method to explicitly send messages
    @MessageMapping("/chat.sendMessage") // Client sends to /app/chat.sendMessage
    // REMOVED: @SendTo("/topic/room/{roomId}") // No longer needed with manual send
    public void sendWebSocketMessage(@Payload ChatMessageRequest message) { // Changed return type to void
        Long roomId = message.getRoomId(); // Get roomId directly from the payload
        logger.info("Received WebSocket message from userId: {} for roomId: {}, content: {}",
                message.getUserId(), roomId, message.getContent());

        // Save the message to the database
        ChatMessageResponse response = messageService.saveMessage(message);

        // Manually send the message to the specific room topic using SimpMessagingTemplate
        // This ensures the message goes to the correct topic dynamically
        messagingTemplate.convertAndSend("/topic/room/" + roomId, response);
        logger.info("WebSocket message saved and manually sent to topic /topic/room/{}. Username: {}, Content: {}",
                roomId, response.getUsername(), response.getContent());
    }
}