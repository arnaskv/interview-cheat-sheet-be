package com.interview.manager.backend.repositories;


import com.interview.manager.backend.models.Comment;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface CommentRepository extends ListCrudRepository<Comment, UUID> {}
