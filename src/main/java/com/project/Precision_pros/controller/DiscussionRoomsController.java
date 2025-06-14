package com.project.Precision_pros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.RoomRequest;
import com.project.Precision_pros.payload.response.RoomResponse;
import com.project.Precision_pros.payload.response.UserInfoResponse;
import com.project.Precision_pros.repository.UserRepository;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.RoomService;

@RestController
@RequestMapping("/api")
public class DiscussionRoomsController {
	@Autowired
    private  RoomService roomService;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/communities/{communityId}/rooms")
    public ResponseEntity<List<RoomResponse>> getRoomsByCommunity(@PathVariable Long communityId,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
        
    	
    	return ResponseEntity.ok(roomService.getRoomsByCommunityAndUser(communityId,username));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> deleteRoomById(@PathVariable Long roomId,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        roomService.DeleteRoomById(roomId,user);
        return ResponseEntity.ok("Room deleted successfully.");
    }

    @PostMapping("/communities/{communityId}/rooms")
    public ResponseEntity<RoomResponse> createRoom(@PathVariable Long communityId,
                                                   @RequestBody RoomRequest request,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(communityId, request,user));
    }
    
    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponse> UpdateRoom(@PathVariable Long roomId,
                                                   @RequestBody String name,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.updateRoomById(roomId,name,user));
    }



    @PostMapping("/rooms/{roomId}/members")
    
    public ResponseEntity<Void> addMembers(@PathVariable Long roomId,
                                           @RequestBody List<String> usernames,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
        roomService.addMembersToRoom(roomId, usernames, username);
        return ResponseEntity.ok().build();
    }
    

		@GetMapping("/rooms/{roomId}/users")
		public ResponseEntity<List<UserInfoResponse>> getUsersInRoom(@PathVariable Long roomId) {
		return ResponseEntity.ok(roomService.getUsersInRoom(roomId));
		}

}

