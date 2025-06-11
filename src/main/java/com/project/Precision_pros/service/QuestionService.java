package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.Community;
import com.project.Precision_pros.model.Question;
import com.project.Precision_pros.model.QuestionStatus;
import com.project.Precision_pros.model.QuestionTags;
import com.project.Precision_pros.model.Tag;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.QuestionRequest;
import com.project.Precision_pros.payload.response.QuestionResponse;
import com.project.Precision_pros.repository.CommunityRepository;
import com.project.Precision_pros.repository.QuestionRepository;
import com.project.Precision_pros.repository.QuestionTagsRepository;
import com.project.Precision_pros.repository.TagRepository;
import com.project.Precision_pros.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionTagsRepository questionTagsRepository;
    
	public List<QuestionResponse> getAllQuestionsByCommunity(String communityCode) {
		List<Question> questions = questionRepository.findByCommunity_CommunityCode(communityCode);
		System.out.print(questions);
		
		return questions.stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public QuestionResponse getQuestionById(Long questionId) {
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new EntityNotFoundException("Question not found"));
		
		return mapToResponse(question);
	}

	public QuestionResponse createQuestion(String communityCode, String username, QuestionRequest request) {
		Community community = communityRepository.findByCommunityCode(communityCode)
				.orElseThrow(() -> new EntityNotFoundException("Community not found"));

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
	
		Question question = new Question();
		question.setTitle(request.getTitle());
		question.setDescription(request.getDescription());
		question.setCommunity(community);
		question.setUser(user);
		question.setCreationDate(LocalDateTime.now());
		question.setLastEditedDate(LocalDateTime.now());
		question.setStatus(QuestionStatus.OPEN);
		
		Question saved = questionRepository.save(question);
        List<String> tagResponses = new ArrayList<>();

        for (String tagName : request.getTags()) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> tagRepository.save(new Tag(tagName)));

            QuestionTags questionTag = new QuestionTags();
            questionTag.setQuestion(question);
            questionTag.setTag(tag);
            questionTagsRepository.save(questionTag);
        }
		return mapToResponse(saved);
	}

	public QuestionResponse updateQuestion(Long questionId, String username, QuestionRequest request) {
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new EntityNotFoundException("Question not found"));

		if (!question.getUser().getUsername().equals(username)) {
			throw new SecurityException("Unauthorized to update this question");
		}

		question.setTitle(request.getTitle());
		question.setDescription(request.getDescription());
		question.setLastEditedDate(LocalDateTime.now());

		Question updated = questionRepository.save(question);
		return mapToResponse(updated);
	}

	public void deleteQuestion(Long questionId, String username) {
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new EntityNotFoundException("Question not found"));

		if (!question.getUser().getUsername().equals(username)) {
			throw new SecurityException("Unauthorized to delete this question");
		}

		questionRepository.delete(question);
	}

	private QuestionResponse mapToResponse(Question question) {

		List<QuestionTags> questionTags = questionTagsRepository.findByQuestion_QuestionId(question.getQuestionId());
		List<String> tagNames = questionTags.stream().map(qt -> qt.getTag().getName())
		.collect(Collectors.toList());

		return new QuestionResponse(question.getQuestionId(), question.getTitle(), question.getDescription(),
				question.getCreationDate(), question.getLastEditedDate(), question.getUser().getUsername(),
				question.getCommunity().getCommunityCode(), question.getStatus(),tagNames);
	}
	public List<QuestionResponse> getQuestionsByTags(List<String> tagNames) {
	    List<Question> questions = questionTagsRepository.findQuestionsByTagNames(tagNames);
	    return questions.stream()
	        .map(this::mapToResponse) // Include tags in this method
	        .collect(Collectors.toList());
	}

	
}




  


