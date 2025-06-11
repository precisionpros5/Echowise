package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.Precision_pros.model.Question;
import com.project.Precision_pros.model.QuestionTags;



@Repository
public interface QuestionTagsRepository extends JpaRepository<QuestionTags, Long> {
	List<QuestionTags> findByQuestion_QuestionId(Long questionId);
	void deleteByQuestion_QuestionId(Long questionId);
	@Query("SELECT qt.question FROM QuestionTags qt WHERE qt.tag.name IN :tagNames")
	    List<Question> findQuestionsByTagNames(@Param("tagNames") List<String> tagNames);
	
}


