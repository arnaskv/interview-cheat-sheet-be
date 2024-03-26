package com.interview.manager.backend.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.model.Comment;
import com.interview.manager.backend.repository.CommentRepository;
import com.interview.manager.backend.service.mapper.CommentMapper;

@Service
public class CommentService {
  @Autowired
  CommentRepository commentRepository;

  @Autowired
  CommentMapper commentMapper;
  
  public Optional<CommentDto> getById(UUID id) {
      return commentRepository.findById(id)
        .map(commentMapper::map);
    }
    
    public CommentDto createComment(CreateUpdateCommentDto createUpdateCommentDto) {
        Comment newComment = CommentMapper.map(createUpdateCommentDto);
    newComment = commentRepository.save(newComment);
    return this.getById(newComment.getId()).orElseThrow(IllegalStateException::new);
  }
  
  public void updateComment(UUID id, CreateUpdateCommentDto createUpdateCommentDto) {
    Comment updatedComment = CommentMapper.map(createUpdateCommentDto);
    updatedComment.setId(id);
    commentRepository.save(updatedComment);
  }
  
  public void deleteComment(UUID id) {
    commentRepository.deleteById(id);
  }
}
