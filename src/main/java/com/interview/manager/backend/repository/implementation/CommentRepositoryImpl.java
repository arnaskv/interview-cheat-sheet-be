package com.interview.manager.backend.repository.implementation;

import com.interview.manager.backend.model.Comment;
import com.interview.manager.backend.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
  private static List<Comment> oversimplifiedCommentTable = new ArrayList<>();

  @Override
  public Optional<Comment> getById(UUID id) {
    return oversimplifiedCommentTable.stream().filter(comment -> comment.getId().equals(id)).findFirst();
  }

  @Override
  public int insert(Comment comment) {
    comment.setDateCreated(OffsetDateTime.now());
    comment.setDateModified(OffsetDateTime.now());
    oversimplifiedCommentTable.add(comment);

    return oversimplifiedCommentTable.indexOf(comment);
  }

  @Override
  public int update(Comment comment) {
    Comment commentToUpdate = getById(comment.getId()).orElse(null);
    if (commentToUpdate == null) {
      insert(comment);
  } else {
      oversimplifiedCommentTable.remove(commentToUpdate);
      commentToUpdate.setContent(comment.getContent());
      commentToUpdate.setDateModified(OffsetDateTime.now());

      oversimplifiedCommentTable.add(commentToUpdate);
  }

  return oversimplifiedCommentTable.indexOf(commentToUpdate);
  }

  @Override
  public void delete(UUID id) {
    getById(id)
      .ifPresent(commentToDelete -> oversimplifiedCommentTable.remove(commentToDelete));
  }
}
