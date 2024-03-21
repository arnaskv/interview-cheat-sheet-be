package com.interview.manager.backend.repository;

import com.interview.manager.backend.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository {

  Optional<Comment> getById(UUID id);

  int insert(Comment comment);

  int update(Comment comment);

  void delete(UUID id);
}
