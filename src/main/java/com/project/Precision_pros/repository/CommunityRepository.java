package com.project.Precision_pros.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.project.Precision_pros.model.Community;
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
		boolean existsByCommunityCode(Long code);

		List<Community> findByCreatorUser_Id(Long userId);

		Optional<Community> findByCommunityCode(Long code);
}
