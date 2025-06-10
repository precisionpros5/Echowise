package com.project.Precision_pros.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
 
import java.time.LocalDateTime;


 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomMemberId;
 
    public Integer getRoomMemberId() {
		return roomMemberId;
	}

	public void setRoomMemberId(Integer roomMemberId) {
		this.roomMemberId = roomMemberId;
	}

	@NotNull
    
    private Long userId;
 
    @NotNull
    private Long roomId;
 
    private LocalDateTime joinDate;
 
  
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public Long getRoomId() {
        return roomId;
    }
 
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
 
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
 
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}