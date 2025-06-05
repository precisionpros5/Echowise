package com.project.Precision_pros.model;

import javax.persistence.*;
@Entity
@Table(name = "QuestionTags", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"question_id", "tag_id"})
})
public class QuestionTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_tag_id")
    private Integer questionTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    // Constructors
    public QuestionTags() {}

    public QuestionTags(Question question, Tag tag) {
        this.question = question;
        this.tag = tag;
    }

    // Getters and Setters
    public Integer getQuestionTagId() {
        return questionTagId;
    }

    public void setQuestionTagId(Integer questionTagId) {
        this.questionTagId = questionTagId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}

