package com.project.Precision_pros.payload.response;

public class CommunityResponse {

    private String name;
    private Long code;
    private String description;


    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CommunityResponse(String name, Long code) {
        
        this.name = name;
        this.code = code;
    }
    public CommunityResponse(String name, Long code,String desc) {
        
        this.name = name;
        this.code = code;
        this.description=desc;
    }



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

    
}

