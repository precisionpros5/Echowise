package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.project.Precision_pros.model.CommunityMember;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
	boolean existsByUserIdAndCommunityId(Long userId, String communityId);

	boolean existsByCommunityIdAndUserId(String communityCode, Long id);


}

