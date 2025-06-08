package com.project.Precision_pros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.CommunityRequest;
import com.project.Precision_pros.payload.response.CommunityResponse;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.CommunityService;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

	    @Autowired
	    private CommunityService communityService;

	    @Autowired
	    private JwtUtils jwtUtil;

	    @Autowired
	    private UserRepository userRepository;

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
	}


