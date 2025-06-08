package com.project.Precision_pros.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
 
import java.time.LocalDateTime;
 

@Entity
@Table(name = "CommunityMembers")
public class CommunityMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long communityMemberId;

	private Long userId;
	private String communityId; // Match with communityCode
	private LocalDateTime joinDate;

 
 
 
    public Long getCommunityMemberId() {
		return communityMemberId;
	}

	public void setCommunityMemberId(Long communityMemberId) {
		this.communityMemberId = communityMemberId;
	}

	public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public String getCommunityId() {
        return communityId;
    }
 
    public void setCommunityId(String string) {
        this.communityId = string;
    }
 
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
 
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
