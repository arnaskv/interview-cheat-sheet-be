package com.interview.manager.backend.services.comment.mapper;

import com.interview.manager.backend.dto.CommentRequestDto;
import com.interview.manager.backend.models.Comment;
import com.interview.manager.backend.dto.CommentResponseDto;

import com.interview.manager.backend.models.InterviewQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Comment requestDtoToComment(CommentRequestDto commentRequestDto, InterviewQuestion question);
    CommentResponseDto commentToResponseDto(Comment comment);
}
