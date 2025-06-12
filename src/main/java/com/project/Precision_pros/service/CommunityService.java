package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.Community;
import com.project.Precision_pros.model.CommunityMember;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.CommunityRequest;
import com.project.Precision_pros.payload.request.UpdateCommunityRequest;
import com.project.Precision_pros.payload.response.CommunityResponse;
import com.project.Precision_pros.payload.response.UserInfoResponse;
import com.project.Precision_pros.repository.CommunityMemberRepository;
import com.project.Precision_pros.repository.CommunityRepository;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private CommunityMemberRepository communityMemberRepository;

    public CommunityResponse createCommunity(CommunityRequest request, User user) {
        Long code = generateUniqueCode();

        Community community = new Community();
        community.setName(request.getName());
        community.setDescription(request.getDescription());
        community.setCommunityCode(code);
        community.setCreationDate(LocalDateTime.now());
        community.setCreatorUser(user);

        Community saved = communityRepository.save(community);
//        CommunityMember member = new CommunityMember();
//        member.setUserId(saved.getCreatorUserId());
//        member.setCommunityId(saved.getCommunityCode());
//        member.setJoinDate(LocalDateTime.now());
//        communityMemberRepository.save(member);
        return new CommunityResponse(saved.getName(),saved.getCommunityCode(),saved.getDescription());
    }

    private Long generateUniqueCode() {
        Random random = new Random();
        String code;
        do {
            code = String.format("%06d", random.nextInt(1000000));
        } while (communityRepository.existsByCommunityCode(Long.parseLong(code)));
        return Long.parseLong(code);
    }
    

    public List<CommunityResponse> getCommunitiesInvolved(Long userId) {
        List<Community> communities = communityRepository.findByCreatorUser_Id(userId);
        
        List<CommunityResponse> res = communities.stream()
            .map(c -> new CommunityResponse(c.getName(),c.getCommunityCode(),c.getDescription()))
            .collect(Collectors.toList());
        return res;
    }


    public String joinCommunity(Long string, User user) {
        Community community = communityRepository.findByCommunityCode(string).orElseThrow(() -> new RuntimeException("Community not found"));

        boolean alreadyJoined = communityMemberRepository.existsByUser_IdAndCommunity_CommunityCode(user.getId(), string);
        if (alreadyJoined) {
            return "User already a member of this community.";
        }
      //  System.out.print(string);
        CommunityMember member = new CommunityMember();
        member.setUser(user);
        member.setCommunity(community);
        member.setJoinDate(LocalDateTime.now());
        communityMemberRepository.save(member);

        return "Joined community successfully.";
    }
    

    public void deleteCommunity(Long communityCode, Long userId) {
		Community community = communityRepository.findByCommunityCode(communityCode).orElseThrow(() -> new RuntimeException("Community not found"));

		if (!community.getCreatorUser().getId().equals(userId)) {
				throw new RuntimeException("Unauthorized: Only the creator can delete this community.");
			}

		communityRepository.delete(community);
}

public void updateCommunity(Long communityCode, Long userId, UpdateCommunityRequest request) {
	Community community = communityRepository.findByCommunityCode(communityCode)
	.orElseThrow(() -> new RuntimeException("Community not found"));
    //System.out.print(userId+""+community.getCreatorUser());
	if (!community.getCreatorUser().getId().equals(userId)) {
	throw new RuntimeException("Unauthorized: Only the creator can update this community.");
	}

	community.setName(request.getName());
	 community.setDescription(request.getDescription());
	communityRepository.save(community);
}

public CommunityResponse getCommunity(Long communityCode) {
	Community c = communityRepository.findByCommunityCode(communityCode)
			.orElseThrow(() -> new RuntimeException("Community not found"));
	return new CommunityResponse(c.getName(),c.getCommunityCode(),c.getDescription());
}

public List<UserInfoResponse> getUsersInCommunity(Long communityCode) {
    List<User> users = communityMemberRepository.findUsersByCommunityCode(communityCode);
    return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
}

private UserInfoResponse mapToUserResponse(User user1) {
	
	return new UserInfoResponse(user1.getId(),user1.getUsername(),user1.getEmail());
}



}

