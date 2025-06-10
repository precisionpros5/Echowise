package com.project.Precision_pros.payload.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerResponse {
    private Long answerId;
    private String content;
    private String username;
    private LocalDateTime creationDate;
    private Boolean isAccepted;
    private Integer voteCount;
    private Boolean hasVoted;
    
    
    
	public Integer getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
	public Boolean getHasVoted() {
		return hasVoted;
	}
	public void setHasVoted(Boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public Boolean getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
    
}
