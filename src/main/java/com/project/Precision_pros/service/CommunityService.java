package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.Community;
import com.project.Precision_pros.payload.request.CommunityRequest;
import com.project.Precision_pros.payload.response.CommunityResponse;
import com.project.Precision_pros.repository.CommunityRepository;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    public CommunityResponse createCommunity(CommunityRequest request, Long creatorUserId) {
        String code = generateUniqueCode();

        Community community = new Community();
        community.setName(request.getName());
        community.setDescription(request.getDescription());
        community.setCommunityCode(code);
        community.setCreationDate(LocalDateTime.now());
        community.setCreatorUserId(creatorUserId);

        Community saved = communityRepository.save(community);
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
}

