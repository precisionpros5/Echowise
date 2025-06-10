package com.project.Precision_pros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.payload.request.VoteRequest;
import com.project.Precision_pros.payload.response.VoteResponse;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.VoteService;

@RestController
@RequestMapping("api/answers/")
public class VoteController {
	
	@Autowired
    private JwtUtils jwtUtil;
	
	@Autowired
	private VoteService voteService;

	@PostMapping("/{answerId}/vote")
	public ResponseEntity<VoteResponse> voteOnAnswer(
			@PathVariable Long answerId, @RequestBody VoteRequest voteRequest,
			 @CookieValue("precisionPros") String jwtToken) {

        	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
			voteService.voteOnAnswer(answerId, voteRequest,username);
			return ResponseEntity.ok(new VoteResponse("Vote recorded successfully."));
	}

}
