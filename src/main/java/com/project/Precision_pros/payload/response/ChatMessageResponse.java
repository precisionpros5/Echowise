package com.project.Precision_pros.payload.response;

import java.time.LocalDateTime;

public class ChatMessageResponse {
    private String username;
    private String content;
	private LocalDateTime timestamp;
	private Long userid;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public ChatMessageResponse(String username, String content, LocalDateTime timestamp,Long userId) {
		
		this.username = username;
		this.content = content;
		this.timestamp = timestamp;
		this.userid=userId;
	}
}
 
