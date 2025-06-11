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
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
	private Community community; // Match with communityCode
	private LocalDateTime joinDate;

 
 
 
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Long getCommunityMemberId() {
		return communityMemberId;
	}

	public void setCommunityMemberId(Long communityMemberId) {
		this.communityMemberId = communityMemberId;
	}


 

 
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
 
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
