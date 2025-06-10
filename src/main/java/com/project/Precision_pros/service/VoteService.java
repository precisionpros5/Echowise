package com.project.Precision_pros.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.Answer;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.model.Vote;
import com.project.Precision_pros.payload.request.VoteRequest;
import com.project.Precision_pros.repository.AnswerRepository;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    public void voteOnAnswer(Long answerId, VoteRequest voteRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        	System.out.println("working here");
      voteRepository.findByUserAndAnswer(user, answer).ifPresent(v -> {
        	throw new RuntimeException("You have already voted on this answer.");
        });

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setAnswer(answer);
        vote.setVoteType(voteRequest.getVoteType());
        vote.setVoteDate(LocalDateTime.now());

        voteRepository.save(vote);
    }
}
