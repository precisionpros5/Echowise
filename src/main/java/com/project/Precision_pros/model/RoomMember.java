package com.project.Precision_pros.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
import jakarta.validation.constraints.NotNull;
 
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMember {
 
    private Integer roomMemberId;
 
    @NotNull
    private Integer userId;
 
    @NotNull
    private Integer roomId;
 
    private LocalDateTime joinDate;
 
    public Integer getRoomMemberId() {
        return roomMemberId;
    }
 
    public void setRoomMemberId(Integer roomMemberId) {
        this.roomMemberId = roomMemberId;
    }
 
    public Integer getUserId() {
        return userId;
    }
 
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
 
    public Integer getRoomId() {
        return roomId;
    }
 
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
 
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
 
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}