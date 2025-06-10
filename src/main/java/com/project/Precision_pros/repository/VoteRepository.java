package com.project.Precision_pros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Precision_pros.model.Answer;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.model.Vote;
import com.project.Precision_pros.model.VoteType;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	Optional<Vote> findByUserAndAnswer(User user, Answer answer);
	long countByAnswerAndVoteType(Answer answer, VoteType voteType);


}
