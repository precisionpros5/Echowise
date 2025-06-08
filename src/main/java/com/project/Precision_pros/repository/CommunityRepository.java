package com.project.Precision_pros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.Precision_pros.model.Community;
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
		boolean existsByCommunityCode(String code);
}
