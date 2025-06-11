package com.project.Precision_pros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.Precision_pros.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
			Optional<Tag> findByName(String name);
			@Query("SELECT t.name FROM Tag t")
			List<String> findAllTagNames();





}

