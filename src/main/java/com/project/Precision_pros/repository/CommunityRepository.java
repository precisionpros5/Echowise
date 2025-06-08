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
		boolean existsByCommunityCode(String code);
		@Query("SELECT c FROM Community c WHERE c.creatorUserId = :userId OR c.communityCode IN (SELECT cm.communityId FROM CommunityMember cm WHERE cm.userId = :userId)")
		List<Community> findCommunitiesInvolvedByUserId(@Param("userId") Long userId);
		Optional<Community> findByCommunityCode(String code);
}
