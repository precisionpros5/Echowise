package com.project.Precision_pros.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", nullable = false)
    private DiscussionRoom room;
 
    private LocalDateTime joinDate;
 
  
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 

 
    public DiscussionRoom getRoom() {
		return room;
	}

	public void setRoom(DiscussionRoom room) {
		this.room = room;
	}

	public LocalDateTime getJoinDate() {
        return joinDate;
    }
 
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}