package com.project.Precision_pros.payload.response;

import com.project.Precision_pros.model.QuestionStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionResponse {
	private Long questionId;
	private String title;
	private String description;
	private LocalDateTime creationDate;
	private LocalDateTime lastEditedDate;
	private String username;
	private String communityCode;
	private QuestionStatus status;
	private List<String> tags;

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public QuestionResponse(Long questionId, String title, String description, LocalDateTime creationDate,
			LocalDateTime lastEditedDate, String username, String communityCode, QuestionStatus status,List<String> tags) {
		super();
		this.questionId = questionId;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.lastEditedDate = lastEditedDate;
		this.username = username;
		this.communityCode = communityCode;
		this.status = status;
		this.tags=tags;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getLastEditedDate() {
		return lastEditedDate;
	}

	public void setLastEditedDate(LocalDateTime lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public QuestionStatus getStatus() {
		return status;
	}

	public void setStatus(QuestionStatus status) {
		this.status = status;
	}

}