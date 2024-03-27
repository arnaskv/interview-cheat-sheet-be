package com.interview.manager.backend.services.comment;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.models.Comment;
import com.interview.manager.backend.repositories.CommentRepository;
import com.interview.manager.backend.services.comment.mapper.CommentMapper;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    public List<CommentDto> getAll() {
        return commentRepository.findAll()
            .stream()
            .map(commentMapper::map)
            .toList();
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
