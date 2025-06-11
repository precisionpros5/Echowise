package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.Precision_pros.model.DiscussionRoom;
import com.project.Precision_pros.model.RoomMember;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Integer> {
	

	@Query(value = "SELECT dr.* FROM discussion_rooms dr JOIN room_member rm ON dr.room_id = rm.room_id WHERE rm.user_id = :userId AND dr.community_id = :communityId", nativeQuery = true)

	List<DiscussionRoom> findRoomsByUserIdAndCommunityId(@Param("userId") Long userId, @Param("communityId") Long communityId);

	boolean existsByRoom_RoomIdAndUserId(Long roomId, Long id);

} 
