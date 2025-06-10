package com.project.Precision_pros.payload.response;


public class RoomResponse {
    private Integer id;
    private String name;
    private String communityId;
    public RoomResponse(Integer id, String name, String communityId) {
		this.id = id;
		this.name = name;
		this.communityId = communityId;
	}
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	
}

