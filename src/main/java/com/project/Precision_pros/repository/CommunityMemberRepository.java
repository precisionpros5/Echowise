package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.Precision_pros.model.Community;
import com.project.Precision_pros.model.CommunityMember;
import com.project.Precision_pros.model.User;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
	boolean existsByUser_IdAndCommunity_CommunityCode(Long userId, Long communityCode);

	boolean existsByCommunity_CommunityCodeAndUser_Id(Long communityCode, Long id);

	@Query("SELECT cm.user FROM CommunityMember cm WHERE cm.community.communityCode = :communityCode")
	List<User> findUsersByCommunityCode(@Param("communityCode") Long communityCode);

	@Query("SELECT cm.community FROM CommunityMember cm WHERE cm.user.id = :userId")
	List<Community> findCommunitiesByUserId(@Param("userId") Long userId);

}
