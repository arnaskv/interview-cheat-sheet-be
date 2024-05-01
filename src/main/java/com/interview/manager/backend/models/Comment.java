package com.interview.manager.backend.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "interview_comments", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String content;

    @Column(name = "date_created", nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(name = "date_modified", nullable = false)
    @LastModifiedDate
    private OffsetDateTime dateModified;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    private InterviewQuestion question;

    @PrePersist
    public void onPrePersist() {
        this.setDateCreated(OffsetDateTime.now());
        this.setDateModified(OffsetDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setDateModified(OffsetDateTime.now());
    }
}
