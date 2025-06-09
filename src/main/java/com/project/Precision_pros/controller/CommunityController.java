package com.project.Precision_pros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.CommunityRequest;
import com.project.Precision_pros.payload.request.JoinCommunityRequest;
import com.project.Precision_pros.payload.request.QuestionRequest;
import com.project.Precision_pros.payload.request.UpdateCommunityRequest;
import com.project.Precision_pros.payload.response.CommunityResponse;
import com.project.Precision_pros.payload.response.QuestionResponse;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.CommunityService;
import com.project.Precision_pros.service.QuestionService;

@RestController
@RequestMapping("/api/communities")

public class CommunityController {

	    @Autowired
	    private CommunityService communityService;

	    @Autowired
	    private JwtUtils jwtUtil;

	    @Autowired
	    private UserRepository userRepository;

		@Autowired
		private QuestionService questionService;
	    @PostMapping
	    public ResponseEntity<?> createCommunity(
	            @RequestBody CommunityRequest request,
	            @CookieValue("precisionPros") String jwtToken) {

	        String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
	        User user = userRepository.findByUsername(username)
	                                  .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        

	        CommunityResponse response = communityService.createCommunity(request, user.getId());
	        return ResponseEntity.ok(response);
	    }
	    

	@GetMapping
	public ResponseEntity<?> getUserCommunities(@CookieValue("precisionPros") String jwtToken) {
	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
    User user = userRepository.findByUsername(username)
                              .orElseThrow(() -> new RuntimeException("User not found"));

				List<CommunityResponse> communities = communityService.getCommunitiesInvolved(user.getId());
				return ResponseEntity.ok(communities);
				
	}
	@GetMapping("/detail/{id}")
	public ResponseEntity<?> getCommunitiesDetails(@PathVariable("id") String communityCode) {
	

				CommunityResponse communities = communityService.getCommunity(communityCode);
				return ResponseEntity.ok(communities);
				
	}

	
	    @PostMapping("/join")
	    public ResponseEntity<?> joinCommunity(@RequestBody JoinCommunityRequest request,
	                                           @CookieValue("precisionPros") String jwtToken) {
	    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
	        User user = userRepository.findByUsername(username)
	                                  .orElseThrow(() -> new RuntimeException("User not found"));

	    			
	        String result = communityService.joinCommunity(request.getCommunityCode(), user.getId());
	        return ResponseEntity.ok(result);
	    
	    }
	        @PutMapping("/{id}")
	        public ResponseEntity<?> updateCommunity(@PathVariable("id") String communityCode,
	                                                 @RequestBody UpdateCommunityRequest request,
	                                                 @CookieValue("precisionPros") String jwtToken) {
	        	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		        User user = userRepository.findByUsername(username)
		                                  .orElseThrow(() -> new RuntimeException("User not found"));

	            communityService.updateCommunity(communityCode, user.getId(), request);
	            return ResponseEntity.ok("Community updated successfully.");
	        }

	        @DeleteMapping("/{id}")
	        public ResponseEntity<?> deleteCommunity(@PathVariable("id") String communityCode,
	                                                 @CookieValue("precisionPros") String jwtToken) {
	        	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		        User user = userRepository.findByUsername(username)
		                                  .orElseThrow(() -> new RuntimeException("User not found"));

	            communityService.deleteCommunity(communityCode, user.getId());
	            return ResponseEntity.ok("Community deleted successfully.");
	        }
	        }






