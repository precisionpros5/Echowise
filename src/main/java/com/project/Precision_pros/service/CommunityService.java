package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.Community;
import com.project.Precision_pros.model.CommunityMember;
import com.project.Precision_pros.payload.request.CommunityRequest;
import com.project.Precision_pros.payload.request.UpdateCommunityRequest;
import com.project.Precision_pros.payload.response.CommunityResponse;
import com.project.Precision_pros.repository.CommunityMemberRepository;
import com.project.Precision_pros.repository.CommunityRepository;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private CommunityMemberRepository communityMemberRepository;

    public CommunityResponse createCommunity(CommunityRequest request, Long creatorUserId) {
        String code = generateUniqueCode();

        Community community = new Community();
        community.setName(request.getName());
        community.setDescription(request.getDescription());
        community.setCommunityCode(code);
        community.setCreationDate(LocalDateTime.now());
        community.setCreatorUserId(creatorUserId);

        Community saved = communityRepository.save(community);
//        CommunityMember member = new CommunityMember();
//        member.setUserId(saved.getCreatorUserId());
//        member.setCommunityId(saved.getCommunityCode());
//        member.setJoinDate(LocalDateTime.now());
//        communityMemberRepository.save(member);
        return new CommunityResponse(saved.getName(),saved.getCommunityCode());
    }

    private String generateUniqueCode() {
        Random random = new Random();
        String code;
        do {
            code = String.format("%06d", random.nextInt(1000000));
        } while (communityRepository.existsByCommunityCode(code));
        return code;
    }
    

    public List<CommunityResponse> getCommunitiesInvolved(Long userId) {
        List<Community> communities = communityRepository.findCommunitiesInvolvedByUserId(userId);
        
        List<CommunityResponse> res = communities.stream()
            .map(c -> new CommunityResponse(c.getName(),c.getCommunityCode(),c.getDescription()))
            .collect(Collectors.toList());
        return res;
    }


    public String joinCommunity(String communityCode, Long userId) {
        Community community = communityRepository.findByCommunityCode(communityCode).orElseThrow(() -> new RuntimeException("Community not found"));

        boolean alreadyJoined = communityMemberRepository.existsByUserIdAndCommunityId(userId, communityCode);
        if (alreadyJoined) {
            return "User already a member of this community.";
        }

        CommunityMember member = new CommunityMember();
        member.setUserId(userId);
        member.setCommunityId(communityCode);
        member.setJoinDate(LocalDateTime.now());
        communityMemberRepository.save(member);

        return "Joined community successfully.";
    }
    

    public void deleteCommunity(String communityCode, Long userId) {
		Community community = communityRepository.findByCommunityCode(communityCode).orElseThrow(() -> new RuntimeException("Community not found"));

		if (!community.getCreatorUserId().equals(userId)) {
				throw new RuntimeException("Unauthorized: Only the creator can delete this community.");
			}

		communityRepository.delete(community);
}

public void updateCommunity(String communityCode, Long userId, UpdateCommunityRequest request) {
	Community community = communityRepository.findByCommunityCode(communityCode)
	.orElseThrow(() -> new RuntimeException("Community not found"));

	if (!community.getCreatorUserId().equals(userId)) {
	throw new RuntimeException("Unauthorized: Only the creator can update this community.");
	}

	community.setName(request.getName());
	 community.setDescription(request.getDescription());
	communityRepository.save(community);
}

public CommunityResponse getCommunity(String communityCode) {
	Community c = communityRepository.findByCommunityCode(communityCode)
			.orElseThrow(() -> new RuntimeException("Community not found"));
	return new CommunityResponse(c.getName(),c.getCommunityCode(),c.getDescription());
}





}

