package com.project.Precision_pros.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class QuestionRequest {
	private String title;
	private String description;
	private List<String> tags; 
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
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
}