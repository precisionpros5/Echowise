package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Precision_pros.model.Community;
import com.project.Precision_pros.model.DiscussionRoom;
import com.project.Precision_pros.model.RoomMember;
import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.RoomRequest;
import com.project.Precision_pros.payload.response.RoomResponse;
import com.project.Precision_pros.repository.CommunityRepository;
import com.project.Precision_pros.repository.RoomMemberRepository;
import com.project.Precision_pros.repository.RoomRepository;
import com.project.Precision_pros.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	@Autowired
    private  RoomRepository roomRepository;
	@Autowired
    private  CommunityRepository communityRepository;
	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private  RoomMemberRepository roomMemberRepository;

    
    public List<RoomResponse> getRoomsByCommunityAndUser(Long communityId, String username) {
    	 User user = userRepository.findByUsername(username)
 				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		List<DiscussionRoom> rooms = roomMemberRepository.findRoomsByUserIdAndCommunityId(user.getId(), communityId);
        return rooms.stream()
                .map(room -> new RoomResponse(
                        room.getRoomId(),
                        room.getName(),
                        room.getCommunity().getCommunityCode()))
                .collect(Collectors.toList());
    }


   
    public RoomResponse getRoomById(Long roomId) {
        DiscussionRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return new RoomResponse(room.getRoomId(), room.getName(),room.getCommunity().getCommunityCode());
    }

  
    public RoomResponse createRoom(Long communityId, RoomRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        User user = userRepository.findById(request.getCreatorUserId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

        DiscussionRoom room = new DiscussionRoom();
        room.setName(request.getName());
        room.setCommunity(community);
        room.setIsPrivate(true);
        room.setCreationDate(LocalDateTime.now());
        room.setCreator(user);	
        
        DiscussionRoom saved = roomRepository.save(room);
        if (request.getMemberUsernames() != null && !request.getMemberUsernames().isEmpty()) {
        		request.getMemberUsernames().add(user.getUsername());
				for (String member : request.getMemberUsernames()) {
			    User muser = userRepository.findByUsername(member)
								.orElseThrow(() -> new EntityNotFoundException("User not found"));
				RoomMember roomMember = new RoomMember();
				roomMember.setRoomId(saved.getRoomId());
				roomMember.setUserId(muser.getId());
				roomMember.setJoinDate(LocalDateTime.now());
				 roomMemberRepository.save(roomMember);
				}

        }

        
        return new RoomResponse(saved.getRoomId(), saved.getName(),community.getCommunityCode());
    }



    
//    public void addMembersToRoom(Long roomId, List<String> newMemberUsernames, String requestingUsername) {
//        DiscussionRoom room = roomRepository.findById(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
//
//        User requestingUser = userRepository.findByUsername(requestingUsername)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        if (!room.getMembers().contains(requestingUser)) {
//            throw new AccessDeniedException("Only room members can add new members.");
//        }
//
//        List<User> newMembers = userRepository.findByUsernameIn(newMemberUsernames);
//        room.getMembers().addAll(newMembers);
//        roomRepository.save(room);
//    }
}

