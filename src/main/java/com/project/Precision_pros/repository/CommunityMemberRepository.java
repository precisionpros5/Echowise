package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.project.Precision_pros.model.CommunityMember;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
	boolean existsByUser_IdAndCommunity_CommunityCode(Long userId, Long communityCode);

	boolean existsByCommunity_CommunityCodeAndUser_Id(Long communityCode, Long id);


}

