package com.project.Precision_pros.payload.response;


public class RoomResponse {
    private Long id;
    private String name;
    private Long communityId;
    private boolean isPrivate;
    public RoomResponse(Long long1, String name, Long communityId,boolean isPrivate) {
		this.id = long1;
		this.name = name;
		this.communityId = communityId;
		this.setIsPrivate(isPrivate);
	}
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	public boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
}

