package com.project.Precision_pros.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
import jakarta.validation.constraints.NotNull;
 
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMember {
 
    private Integer communityMemberId;
 
    @NotNull
    private Integer userId;
 
    @NotNull
    private Integer communityId;
 
    private LocalDateTime joinDate;
 
    public Integer getCommunityMemberId() {
        return communityMemberId;
    }
 
    public void setCommunityMemberId(Integer communityMemberId) {
        this.communityMemberId = communityMemberId;
    }
 
    public Integer getUserId() {
        return userId;
    }
 
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
 
    public Integer getCommunityId() {
        return communityId;
    }
 
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }
 
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
 
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
