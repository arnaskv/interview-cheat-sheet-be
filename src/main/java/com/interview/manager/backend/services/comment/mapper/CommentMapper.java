package com.interview.manager.backend.services.comment.mapper;

import com.interview.manager.backend.dto.CommentRequestDto;
import com.interview.manager.backend.models.Comment;
import com.interview.manager.backend.dto.CommentResponseDto;

import com.interview.manager.backend.models.InterviewQuestion;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public static Comment map(CommentRequestDto commentRequestDto, InterviewQuestion question) {
        return Comment.builder()
            .content(commentRequestDto.getContent())
            .question(question)
            .build();
    }

    public CommentResponseDto map(Comment comment) {
        return CommentResponseDto.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .dateCreated(comment.getDateCreated())
            .dateModified(comment.getDateModified())
            .build();
    }
}
