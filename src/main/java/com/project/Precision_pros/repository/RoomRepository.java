package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.Precision_pros.model.DiscussionRoom;

@Repository
public interface RoomRepository extends JpaRepository<DiscussionRoom, Long> {
    List<DiscussionRoom> findByCommunity_CommunityCode(Long communityId);

    @Query(value = "SELECT room_id FROM discussion_rooms WHERE community_id = :communityId AND is_private = false", nativeQuery = true)
    List<Long> findPublicRoomIdsByCommunityId(@Param("communityId") Long communityId);

}
