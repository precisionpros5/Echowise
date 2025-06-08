package com.project.Precision_pros.payload.response;

public class CommunityResponse {

    private String name;
    private String code;
    private String description;


    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CommunityResponse(String name, String code) {
        
        this.name = name;
        this.code = code;
    }
    public CommunityResponse(String name, String code,String desc) {
        
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    
}

