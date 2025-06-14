package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.DiscussionRoom;
import com.project.Precision_pros.model.Message;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.ChatMessageRequest;
import com.project.Precision_pros.payload.response.ChatMessageResponse;
import com.project.Precision_pros.repository.MessageRepository;
import com.project.Precision_pros.repository.RoomRepository;
import com.project.Precision_pros.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public ChatMessageResponse saveMessage(ChatMessageRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        DiscussionRoom room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        Message message = new Message();
        message.setUser(user);
        message.setRoom(room);
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);

        return new ChatMessageResponse(user.getUsername(), message.getContent(), message.getTimestamp(),message.getUser().getId());
    }

    public List<ChatMessageResponse> getMessagesByRoom(Long roomId) {
        return messageRepository.findByRoom_RoomIdOrderByTimestampAsc(roomId).stream()
                .map(msg -> new ChatMessageResponse(
                        msg.getUser().getUsername(),
                        msg.getContent(),
                        msg.getTimestamp(),msg.getUser().getId()))
                .collect(Collectors.toList());
    }
}

