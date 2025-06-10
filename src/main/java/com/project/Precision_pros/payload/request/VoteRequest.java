package com.project.Precision_pros.payload.request;


import com.project.Precision_pros.model.VoteType;
import jakarta.validation.constraints.NotNull;

public class VoteRequest {
	
    @NotNull
    private VoteType voteType;

    

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

	public VoteType getVoteType() {
		// TODO Auto-generated method stub
		return voteType;
	}
}
