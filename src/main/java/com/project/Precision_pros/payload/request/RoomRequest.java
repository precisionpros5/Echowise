package com.project.Precision_pros.payload.request;

import java.util.List;

public class RoomRequest {
    private String name;
    private String description;
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
	

	
	public List<String> getMemberUsernames() {
		return memberUsernames;
	}
	public void setMemberUsernames(List<String> memberUsernames) {
		this.memberUsernames = memberUsernames;
	}
	public RoomRequest(String name, String description, List<String> memberUsernames) {
		super();
		this.name = name;
		this.description = description;
		this.memberUsernames = memberUsernames;
	}
	
}

