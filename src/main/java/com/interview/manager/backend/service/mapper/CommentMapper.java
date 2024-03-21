package com.interview.manager.backend.service.mapper;

import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
  public static Comment map(CreateUpdateCommentDto createUpdateCommentDto) {
    return Comment.builder()
      .content(createUpdateCommentDto.getContent())
      .build();
  }

  public CommentDto map(Comment comment) {
    return CommentDto.builder()
      .id(comment.getId())
      .content(comment.getContent())
      .dateCreated(comment.getDateCreated())
      .dateModified(comment.getDateModified())
      .build();
  }
}
