package com.interview.manager.backend.repository;

import com.interview.manager.backend.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>{}
