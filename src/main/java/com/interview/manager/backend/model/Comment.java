package com.interview.manager.backend.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "interview_comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false, length = 255)
  private String content;

  @Column(name = "question_id", nullable = false)
  private UUID questionId;

  @Column(name = "date_created")
  private OffsetDateTime dateCreated;

  @Column(name = "date_modified")
  @LastModifiedDate
  private OffsetDateTime dateModified;
}

// create table interview_comments (
//   id uuid default gen_random_uuid() primary key,
//   content varchar(255) not null,
//   date_created timestamp with time zone default CURRENT_TIMESTAMP,
//   date_modified timestamp with time zone default CURRENT_TIMESTAMP);
