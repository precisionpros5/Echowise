package com.project.Precision_pros.model;


 
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
	@OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
	private List<QuestionTags> QuestionTags = new ArrayList<>();

    public Tag(String tagName) {
		this.name=tagName;
	}


protected Tag() {
}


	// Getters and Setters
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

