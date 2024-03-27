package com.interview.manager.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.interview.manager.backend.models.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, UUID> {
    List<Comment> findAll();
}
