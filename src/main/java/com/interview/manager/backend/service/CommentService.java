package com.interview.manager.backend.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.model.Comment;
import com.interview.manager.backend.repository.CommentRepository;
import com.interview.manager.backend.service.mapper.CommentMapper;

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

  public void deleteById(UUID id) {
    Optional<Comment> comment = commentRepository.findById(id);

    if(comment.isPresent()) {
      commentRepository.deleteById(id);
    } else {
      throw new NoSuchElementException("Comment with ID " + id + " not found");
    }
  }
}
