package com.project.Precision_pros.service;

import com.project.Precision_pros.exception.ResourceNotFoundException;
import com.project.Precision_pros.model.Answer;
import com.project.Precision_pros.model.Question;
import com.project.Precision_pros.model.QuestionStatus;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.model.VoteType;
import com.project.Precision_pros.payload.request.AnswerRequest;
import com.project.Precision_pros.payload.request.AnswerUpdateRequest;
import com.project.Precision_pros.payload.response.AnswerResponse;
import com.project.Precision_pros.repository.AnswerRepository;
import com.project.Precision_pros.repository.QuestionRepository;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VoteRepository voteRepository;
    
    
    
    public AnswerResponse postAnswer(Long questionId, String username, AnswerRequest request) {
    	
//    	System.out.println("YOur question Id : "+questionId);
//    	System.out.println(questionRepository.findAll());
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        

        if (question.getStatus() == QuestionStatus.CLOSED) {
        	throw new IllegalStateException("Cannot post an answer to a closed question.");
        }

        if(question.getStatus() == QuestionStatus.OPEN)
        {
        	question.setStatus(QuestionStatus.ANSWERED);
        }

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setContent(request.getContent());
        answer.setCreationDate(LocalDateTime.now());
        answer.setLastEditedDate(LocalDateTime.now());
        answer.setIsAccepted(false);

        Answer saved = answerRepository.save(answer);
        

        long upvotes = voteRepository.countByAnswerAndVoteType(answer, VoteType.UPVOTE);
		long downvotes = voteRepository.countByAnswerAndVoteType(answer, VoteType.DOWNVOTE);
		boolean hasVoted = voteRepository.findByUserAndAnswer(user, answer).isPresent();


        AnswerResponse response = new AnswerResponse();
        response.setAnswerId(saved.getAnswerId());
        response.setContent(saved.getContent());
        response.setUsername(user.getUsername());
        response.setCreationDate(saved.getCreationDate());
        response.setIsAccepted(saved.getIsAccepted());
        response.setVoteCount((int)(upvotes-downvotes));
        response.setHasVoted(hasVoted);
        

        return response;
    }


    public List<AnswerResponse> getAnswersByQuestionId(Long questionId,String username) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question with ID " + questionId + " not found."));

        List<Answer> answers = answerRepository.findByQuestion(question);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return answers.stream().map(answer -> {

        long upvotes = voteRepository.countByAnswerAndVoteType(answer, VoteType.UPVOTE);
        long downvotes = voteRepository.countByAnswerAndVoteType(answer, VoteType.DOWNVOTE);
		boolean hasVoted = voteRepository.findByUserAndAnswer(user, answer).isPresent();

            AnswerResponse response = new AnswerResponse();
            response.setAnswerId(answer.getAnswerId());
            response.setContent(answer.getContent());
            response.setUsername(answer.getUser().getUsername());
            response.setCreationDate(answer.getCreationDate());
            response.setIsAccepted(answer.getIsAccepted());
            response.setVoteCount((int)(upvotes-downvotes));
            response.setHasVoted(hasVoted);
            
            return response;
        }).collect(Collectors.toList());
    }



	public AnswerResponse updateAnswer(Long answerId, String username, AnswerUpdateRequest request) {
		
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
	    Answer answer = answerRepository.findById(answerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Answer with ID " + answerId + " not found."));

	    if (!answer.getUser().getUsername().equals(username)) {
	        throw new AccessDeniedException("You are not authorized to update this answer.");
	    }

	    answer.setContent(request.getContent());
	    answer.setLastEditedDate(LocalDateTime.now());
	    
	    
	   
	    Answer updated = answerRepository.save(answer);
	    

        long upvotes = voteRepository.countByAnswerAndVoteType(answer, VoteType.UPVOTE);
		long downvotes = voteRepository.countByAnswerAndVoteType(answer, VoteType.DOWNVOTE);
		boolean hasVoted = voteRepository.findByUserAndAnswer(user, answer).isPresent();

	    AnswerResponse response = new AnswerResponse();
	    response.setAnswerId(updated.getAnswerId());
	    response.setContent(updated.getContent());
	    response.setUsername(updated.getUser().getUsername());
	    response.setCreationDate(updated.getCreationDate());
	    response.setIsAccepted(updated.getIsAccepted());
	    response.setVoteCount((int)(upvotes-downvotes));
        response.setHasVoted(hasVoted);
	    

	    return response;
	}


	public void deleteAnswer(Long answerId, String username) {
	    Answer answer = answerRepository.findById(answerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Answer with ID " + answerId + " not found."));

	    String answerOwner = answer.getUser().getUsername();
//		    boolean isAdmin = answer.getUser().getRoles().stream()
//		            .anyMatch(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN"));

	    if (!username.equals(answerOwner) /*&& !isAdmin*/) {
	        throw new AccessDeniedException("You are not authorized to delete this answer.");
	    }
	    
	    answerRepository.delete(answer);
	}


	public void acceptAnswer(Long answerId, String username) {
	    Answer answer = answerRepository.findById(answerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Answer with ID " + answerId + " not found."));

	    Question question = answer.getQuestion();

	    if (!question.getUser().getUsername().equals(username)) {
	        throw new AccessDeniedException("Only the question owner can accept an answer.");
	    }

	    // Mark the selected answer as accepted
	    answer.setIsAccepted(true);
	    answerRepository.save(answer);
	}



 }

