
package com.project.Precision_pros.payload.request;

import lombok.Data;

@Data
public class AnswerUpdateRequest {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
