package com.project.Precision_pros.payload.request;

import java.util.List;

public class RoomRequest {
    private String name;
    private String description;
    private Long creatorUserId;
    private List<String> memberUsernames;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getCreatorUserId() {
		return creatorUserId;
	}
	public void setCreatorUserId(Long creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	
	public List<String> getMemberUsernames() {
		return memberUsernames;
	}
	public void setMemberUsernames(List<String> memberUsernames) {
		this.memberUsernames = memberUsernames;
	}
}

