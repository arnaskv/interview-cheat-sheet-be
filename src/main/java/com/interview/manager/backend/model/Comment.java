package com.interview.manager.backend.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.*;
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
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false, length = 255)
  private String content;

  @Column(name = "date_created", nullable = false, updatable = false)
  private OffsetDateTime dateCreated;

  @Column(name = "date_modified", nullable = false)
  @LastModifiedDate
  private OffsetDateTime dateModified;

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
