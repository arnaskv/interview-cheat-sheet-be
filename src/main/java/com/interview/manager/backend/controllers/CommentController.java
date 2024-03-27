package com.interview.manager.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.service.CommentService;

@RestController
@RequestMapping(value = CommentController.COMMENT_ENDPOINT)
public class CommentController {
  public static final String COMMENT_ENDPOINT = "/api/v1/comment";
 
  @Autowired
  CommentService commentService;

  @GetMapping
  public ResponseEntity<List<CommentDto>> getAllComments() {
    List<CommentDto> comments = commentService.getAll();
    return comments.isEmpty()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(comments);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getCommentById(@Validated @PathVariable UUID id) {
    return commentService
      .getById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
}
