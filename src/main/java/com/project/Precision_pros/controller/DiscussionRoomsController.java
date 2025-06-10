package com.project.Precision_pros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Precision_pros.model.User;
import com.project.Precision_pros.payload.request.RoomRequest;
import com.project.Precision_pros.payload.response.RoomResponse;
import com.project.Precision_pros.security.jwt.JwtUtils;
import com.project.Precision_pros.service.RoomService;

@RestController
@RequestMapping("/api")
public class DiscussionRoomsController {
	@Autowired
    private  RoomService roomService;
    @Autowired
    private JwtUtils jwtUtil;
    @GetMapping("/communities/{communityId}/rooms")
    public ResponseEntity<List<RoomResponse>> getRoomsByCommunity(@PathVariable Long communityId,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
        
    	
    	return ResponseEntity.ok(roomService.getRoomsByCommunityAndUser(communityId,username));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @PostMapping("/communities/{communityId}/rooms")
    public ResponseEntity<RoomResponse> createRoom(@PathVariable Long communityId,
                                                   @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(communityId, request));
    }



    @PostMapping("/rooms/{roomId}/members")
    
    public ResponseEntity<Void> addMembers(@PathVariable Long roomId,
                                           @RequestBody List<String> usernames,@CookieValue("precisionPros") String jwtToken) {
    	String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
        roomService.addMembersToRoom(roomId, usernames, username);
        return ResponseEntity.ok().build();
    }
}

