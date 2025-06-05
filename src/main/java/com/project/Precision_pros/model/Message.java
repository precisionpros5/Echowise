package com.project.Precision_pros.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Messages", indexes = {
    @Index(name = "idx_room_id", columnList = "room_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp")
})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private DiscussionRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // Constructors
    public Message() {}

    public Message(DiscussionRoom room, User user, String content) {
        this.room = room;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public DiscussionRoom getRoom() {
        return room;
    }

    public void setRoom(DiscussionRoom room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

