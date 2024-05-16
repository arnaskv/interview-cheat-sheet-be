package com.interview.manager.backend.repositories;


import com.interview.manager.backend.models.Comment;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends ListCrudRepository<Comment, UUID> {

    List<Comment> findAllByOrderByDateCreatedDesc();

    List<Comment> getAllByQuestionIdOrderByDateCreatedDesc(Long questionId);

    int countByQuestionId(Long questionId);
}
