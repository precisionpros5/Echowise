package com.project.Precision_pros.repository;

import com.project.Precision_pros.model.Question;
import com.project.Precision_pros.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCommunity_CommunityCode(String communityCode);
}
