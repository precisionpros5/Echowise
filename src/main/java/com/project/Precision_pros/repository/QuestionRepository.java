package com.project.Precision_pros.repository;

import com.project.Precision_pros.model.Question;
import com.project.Precision_pros.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCommunity_CommunityCode(String communityCode);
   
}
