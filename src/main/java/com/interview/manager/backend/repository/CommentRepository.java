package com.interview.manager.backend.repository;

import com.interview.manager.backend.model.Comment;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, UUID> {}
