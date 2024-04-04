package com.interview.manager.backend.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCommentById(@PathVariable UUID id) {

    try {
      commentService.deleteById(id);
      return ResponseEntity.ok("Comment deleted successfully");
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting a comment");
    }

  }
}
