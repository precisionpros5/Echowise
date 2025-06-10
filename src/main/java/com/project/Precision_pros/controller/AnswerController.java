package com.project.Precision_pros.controller;
import com.project.Precision_pros.payload.request.AnswerRequest;
import com.project.Precision_pros.payload.request.AnswerUpdateRequest;
import com.project.Precision_pros.payload.response.AnswerResponse;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.AnswerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private JwtUtils jwtUtils;

    
    // question status should change --- if question is answered - done
    @PostMapping("/{questionId}/answers")
    public ResponseEntity<AnswerResponse> postAnswer(
            @PathVariable int questionId,
            @RequestBody AnswerRequest request,
            @CookieValue("precisionPros") String jwtToken) {
    	
    	
        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
        AnswerResponse response = answerService.postAnswer(questionId, username, request);
        return ResponseEntity.ok(response);
    }

	@GetMapping("/{questionId}/answers")
	public ResponseEntity<List<AnswerResponse>> getAnswers(@PathVariable int questionId,
			@CookieValue("precisionPros") String jwtToken) {
		
		
		String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
		List<AnswerResponse> responses = answerService.getAnswersByQuestionId(questionId,username );
		return ResponseEntity.ok(responses);
	}
	
	

	@PutMapping("/answers/{id}")//check put 
	public ResponseEntity<AnswerResponse> updateAnswer(
			@PathVariable Long id,
			@RequestBody AnswerUpdateRequest request,
			@CookieValue("precisionPros") String jwtToken) {
		
		String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
		AnswerResponse response = answerService.updateAnswer(id, username, request);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/answers/{id}") // 500 error while using deleteMapping
	public ResponseEntity<String> deleteAnswer(
	        @PathVariable Long id,
	        @CookieValue("precisionPros") String jwtToken) {

	    String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
	    answerService.deleteAnswer(id, username);
	    return ResponseEntity.ok("Answer deleted successfully.");
	}

	@PostMapping("/answers/{id}/accept")
	public ResponseEntity<String> acceptAnswer(
	        @PathVariable Long id,
	        @CookieValue("precisionPros") String jwtToken) {

	    String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
	    answerService.acceptAnswer(id, username);
	    return ResponseEntity.ok("Answer marked as accepted.");
	}

	

}
