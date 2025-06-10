package com.project.Precision_pros.repository;


import com.project.Precision_pros.model.Answer;
import com.project.Precision_pros.model.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	List<Answer> findByQuestion(Question question);

}
