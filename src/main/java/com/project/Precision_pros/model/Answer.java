package com.project.Precision_pros.model;


import java.time.LocalDateTime;

import javax.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @NotNull
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull
    @Size(min = 1, max = 10000)
    private String content;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime creationDate;

    private LocalDateTime lastEditedDate;

    @Column(nullable = false)
    @NotNull
    private Boolean isAccepted;

    // Getters and Setters
    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastEditedDate() {
        return lastEditedDate;
    }

    public void setLastEditedDate(LocalDateTime lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
