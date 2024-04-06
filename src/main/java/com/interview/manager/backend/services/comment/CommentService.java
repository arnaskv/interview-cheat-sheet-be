package com.interview.manager.backend.services.comment;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.models.Comment;
import com.interview.manager.backend.repositories.CommentRepository;
import com.interview.manager.backend.services.comment.mapper.CommentMapper;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    private final CommentMapper commentMapper;

    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream()
            .map(commentMapper::map)
            .collect(Collectors.toList());
    }

    public Optional<CommentDto> getById(UUID id) {
        return commentRepository.findById(id)
            .map(commentMapper::map);
    }

    public CommentDto createComment(CreateUpdateCommentDto createUpdateCommentDto) {
        Comment newComment = CommentMapper.map(createUpdateCommentDto);
        newComment = commentRepository.save(newComment);
        return this.getById(newComment.getId()).orElseThrow(IllegalStateException::new);
    }
}
