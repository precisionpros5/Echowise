package com.project.Precision_pros.controller;

import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.QuestionRequest;
import com.project.Precision_pros.payload.response.QuestionResponse;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.security.services.UserDetailsImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	 @Autowired
	    private JwtUtils jwtUtil;

	    @Autowired
	    private UserRepository userRepository;

	// GET /api/communities/{communityId}/questions
	@GetMapping("/communities/{communityId}/questions")
	public ResponseEntity<List<QuestionResponse>> getQuestionsByCommunity(@PathVariable String communityId) {
		return ResponseEntity.ok(questionService.getAllQuestionsByCommunity(communityId));
	}

	// GET /api/questions/{id}
	@GetMapping("/questions/{id}")
	public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
		return ResponseEntity.ok(questionService.getQuestionById(id));
	}

	// POST /api/communities/{communityId}/questions
	@PostMapping("/communities/{communityId}/questions")
	public ResponseEntity<QuestionResponse> createQuestion(@PathVariable String communityId,
			@RequestBody QuestionRequest request,@CookieValue("precisionPros") String jwtToken) {
		String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
	    User user = userRepository.findByUsername(username)
	                              .orElseThrow(() -> new RuntimeException("User not found"));
		QuestionResponse res=questionService.createQuestion(communityId, username, request);
		return ResponseEntity.ok(res);
	}

	// PUT /api/questions/{id}
	@PutMapping("/questions/{id}")
	public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id,
			@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody QuestionRequest request) {

		String username = userDetails.getUsername();
		return ResponseEntity.ok(questionService.updateQuestion(id, username, request));
	}

	// DELETE /api/questions/{id}
	@DeleteMapping("/questions/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long id,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		String username = userDetails.getUsername();
		questionService.deleteQuestion(id, username);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/by-tags")
	public ResponseEntity<List<QuestionResponse>> getQuestionsByTags(@RequestParam List<String> tags) {
	    return ResponseEntity.ok(questionService.getQuestionsByTags(tags));
	}

}
