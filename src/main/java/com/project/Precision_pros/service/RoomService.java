package com.project.Precision_pros.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.project.Precision_pros.payload.response.UserInfoResponse;
import com.project.Precision_pros.repository.CommunityMemberRepository;
import com.project.Precision_pros.repository.CommunityRepository;
import com.project.Precision_pros.repository.RoomMemberRepository;
import com.project.Precision_pros.repository.RoomRepository;
import com.project.Precision_pros.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private CommunityRepository communityRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoomMemberRepository roomMemberRepository;
	@Autowired
	private CommunityMemberRepository communityMemberRepository;

	public List<RoomResponse> getRoomsByCommunityAndUser(Long communityId, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		List<DiscussionRoom> rooms = roomMemberRepository.findRoomsByUserIdAndCommunityId(user.getId(), communityId);
		return rooms.stream().map(room -> new RoomResponse(room.getRoomId(), room.getName(),
				room.getCommunity().getCommunityCode(), room.getIsPrivate())).collect(Collectors.toList());
	}

	public RoomResponse getRoomById(Long roomId) {
		DiscussionRoom room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
		return new RoomResponse(room.getRoomId(), room.getName(), room.getCommunity().getCommunityCode(),
				room.getIsPrivate());
	}

	public void DeleteRoomById(Long roomId, User user) {
		DiscussionRoom room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
		if (!user.getId().equals(room.getCreator().getId())) {
			throw new RuntimeException("Unauthorized: Only the creator can delete this room.");
		}
		if (!room.getIsPrivate()) {
			throw new RuntimeException("Public Room can't be deleted");
		}
		roomRepository.delete(room);
	}

	public RoomResponse updateRoomById(Long roomId, String name, User user) {
		DiscussionRoom room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

		if (user.getId().equals(room.getCreator().getId())) {
			room.setName(name);
			room.setCommunity(room.getCommunity());

			room.setIsPrivate(room.getIsPrivate());

			room.setCreationDate(room.getCreationDate());
			room.setCreator(room.getCreator());

			DiscussionRoom saved = roomRepository.save(room);
		} else {
			throw new RuntimeException("Only Creator Can Edit The Name And Description");
		}

		return new RoomResponse(room.getRoomId(), room.getName(), room.getCommunity().getCommunityCode(),
				room.getIsPrivate());
	}

	public RoomResponse createRoom(Long communityId, RoomRequest request, User user) {
		Community community = communityRepository.findById(communityId)
				.orElseThrow(() -> new RuntimeException("Community not found"));

		DiscussionRoom room = new DiscussionRoom();
		room.setName(request.getName());
		room.setCommunity(community);
		if ("public Room".equals(request.getName())) {
			room.setIsPrivate(false);
		} else {
			room.setIsPrivate(true);
		}
		room.setCreationDate(LocalDateTime.now());
		room.setCreator(user);

		DiscussionRoom saved = roomRepository.save(room);
		
			
			for (String member : request.getMemberUsernames()) {
				System.out.print(member);
				User muser = userRepository.findByUsername(member)
						.orElseThrow(() -> new EntityNotFoundException("User not found"));
				boolean isRoomMember = roomMemberRepository.existsByRoom_RoomIdAndUserId(room.getRoomId(),muser.getId() );
				if (isRoomMember) {
					throw new IllegalArgumentException(
							"User " + muser.getUsername() + " is existing a member of the room.");
				}
				RoomMember roomMember = new RoomMember();
				roomMember.setRoom(saved);
				roomMember.setUser(muser);
				roomMember.setJoinDate(LocalDateTime.now());
				roomMemberRepository.save(roomMember);
			

		}

		return new RoomResponse(saved.getRoomId(), saved.getName(), saved.getCommunity().getCommunityCode(),
				saved.getIsPrivate());
	}

	public void addMembersToRoom(Long roomId, List<String> newMemberUsernames, String requestingUsername) {
		DiscussionRoom room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

		User requestingUser = userRepository.findByUsername(requestingUsername)
				.orElseThrow(() -> new RuntimeException("User not found"));

		boolean isMember = roomMemberRepository.existsByRoom_RoomIdAndUserId(roomId, requestingUser.getId());
		if (room.getIsPrivate() && !isMember) {
			throw new RuntimeException("Only room members can add new members.");
		}

		List<User> newUsers = userRepository.findByUsernameIn(newMemberUsernames);
		for (User newUser : newUsers) {
			boolean isCommunityMember = communityMemberRepository
					.existsByCommunity_CommunityCodeAndUser_Id(room.getCommunity().getCommunityCode(), newUser.getId());
			boolean isRoomMember = roomMemberRepository.existsByRoom_RoomIdAndUserId(room.getRoomId(), newUser.getId());
			if (!isCommunityMember) {
				throw new IllegalArgumentException(
						"User " + newUser.getUsername() + " is not a member of the community.");
			}
			if (isRoomMember) {
				throw new IllegalArgumentException(
						"User " + newUser.getUsername() + " is existing a member of the room.");
			}

			RoomMember roomMember = new RoomMember();
			roomMember.setRoom(room);
			roomMember.setUser(newUser);
			roomMember.setJoinDate(LocalDateTime.now());
			roomMemberRepository.save(roomMember);
		}
	}

	public List<UserInfoResponse> getUsersInRoom(Long roomId) {
		List<User> users = roomMemberRepository.findUsersByRoomId(roomId);
		return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
	}

	private UserInfoResponse mapToUserResponse(User user1) {

		return new UserInfoResponse(user1.getId(), user1.getUsername(), user1.getEmail());
	}

}
