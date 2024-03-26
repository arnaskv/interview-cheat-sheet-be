package com.interview.manager.backend.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.service.CommentService;

@RestController
@RequestMapping(value = CommentController.COMMENT_ENDPOINT)
public class CommentController {
  public static final String COMMENT_ENDPOINT = "/api/v1/comment";
 
  @Autowired
  CommentService commentService;

  @Value("${quiz.resource-url-format:%s/%s}")
  private String resourceUrlFormat;

  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID id) {
    return commentService
      .getById(id)
      .map(comment -> ResponseEntity.ok(comment))
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<CommentDto> createComment(@RequestBody CreateUpdateCommentDto comment) {
    CommentDto createdComment = commentService.createComment(comment);
    URI resourceUrl = URI.create(String.format(resourceUrlFormat, COMMENT_ENDPOINT, createdComment.getId()));
    return ResponseEntity.created(resourceUrl).body(createdComment);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateComment(@PathVariable UUID id, @RequestBody CreateUpdateCommentDto comment) {
    commentService.updateComment(id, comment);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteComment(@PathVariable UUID id) {
    commentService.deleteComment(id);
  }
}
