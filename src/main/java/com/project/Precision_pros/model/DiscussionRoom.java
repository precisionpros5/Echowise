package com.project.Precision_pros.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "DiscussionRooms", indexes = {
    @Index(name = "idx_community_id", columnList = "community_id"),
    @Index(name = "idx_name", columnList = "name")
})
public class DiscussionRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate = false;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

 

    // Constructors
    public DiscussionRoom() {}

    public DiscussionRoom(Community community, String name, Boolean isPrivate, User creator) {
        this.community = community;
        this.name = name;
        this.isPrivate = isPrivate;
        this.creator = creator;
        
        this.creationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

 
}
