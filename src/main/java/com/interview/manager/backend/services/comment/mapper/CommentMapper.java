package com.interview.manager.backend.services.comment.mapper;

import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.models.Comment;
import com.interview.manager.backend.dto.CommentDto;

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
