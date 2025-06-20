package com.project.Precision_pros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.CommunityRequest;
import com.project.Precision_pros.payload.request.JoinCommunityRequest;
import com.project.Precision_pros.payload.request.QuestionRequest;
import com.project.Precision_pros.payload.request.RoomRequest;
import com.project.Precision_pros.payload.request.UpdateCommunityRequest;
import com.project.Precision_pros.payload.response.CommunityResponse;
import com.project.Precision_pros.payload.response.QuestionResponse;
import com.project.Precision_pros.payload.response.UserInfoResponse;
import com.project.Precision_pros.repository.RoomRepository;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.CommunityService;
import com.project.Precision_pros.service.QuestionService;
import com.project.Precision_pros.service.RoomService;

@RestController
@RequestMapping("/api/communities")

public class CommunityController {

	@Autowired
	private CommunityService communityService;

	@Autowired
	private JwtUtils jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomRepository roomRepository;

	@PostMapping
	public ResponseEntity<?> createCommunity(@RequestBody CommunityRequest request,
			@CookieValue("precisionPros") String jwtToken) {

		String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		CommunityResponse response = communityService.createCommunity(request, user);
		communityService.joinCommunity(response.getCode(), user);

		try {
			List<String> member = List.of(username);
			RoomRequest roomreq = new RoomRequest("public Room", "for all user in Community", member);
			roomService.createRoom(response.getCode(), roomreq, user);
		} catch (Exception e) {
			System.out.print("Room creation failed" + e);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<?> getUserCommunities(@CookieValue("precisionPros") String jwtToken) {
		String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		List<CommunityResponse> communities = communityService.getCommunitiesInvolved(user.getId());
		return ResponseEntity.ok(communities);

	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<?> getCommunitiesDetails(@PathVariable("id") Long communityCode) {

		CommunityResponse communities = communityService.getCommunity(communityCode);
		return ResponseEntity.ok(communities);

	}

	@PostMapping("/join")
	public ResponseEntity<?> joinCommunity(@RequestBody JoinCommunityRequest request,
			@CookieValue("precisionPros") String jwtToken) {
		String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		// System.out.print(username);
		String result = communityService.joinCommunity(request.getCommunityCode(), user);
		try {
			List<String> member = List.of(username);
			List<Long> roomid = roomRepository.findPublicRoomIdsByCommunityId(request.getCommunityCode());
			roomService.addMembersToRoom(roomid.getFirst(), member, username);
		} catch (Exception e) {
			System.out.print("Room Join failed" + e);
		}
		return ResponseEntity.ok(result);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCommunity(@PathVariable("id") Long communityCode,
			@RequestBody UpdateCommunityRequest request, @CookieValue("precisionPros") String jwtToken) {
		String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		communityService.updateCommunity(communityCode, user.getId(), request);
		return ResponseEntity.ok("Community updated successfully.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCommunity(@PathVariable("id") Long communityCode,
			@CookieValue("precisionPros") String jwtToken) {
		String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		communityService.deleteCommunity(communityCode, user.getId());
		return ResponseEntity.ok("Community deleted successfully.");
	}

	@GetMapping("/{communityCode}/users")
	public ResponseEntity<List<UserInfoResponse>> getUsersInCommunity(@PathVariable Long communityCode) {
		return ResponseEntity.ok(communityService.getUsersInCommunity(communityCode));
	}

}
