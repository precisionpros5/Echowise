package com.project.Precision_pros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Precision_pros.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByRoom_RoomIdOrderByTimestampAsc(Long roomId);
}
