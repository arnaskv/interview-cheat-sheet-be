package com.interview.manager.backend.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.manager.backend.dto.CommentDto;
import com.interview.manager.backend.dto.CreateUpdateCommentDto;
import com.interview.manager.backend.services.comment.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = CommentController.COMMENT_ENDPOINT)
public class CommentController {
    public static final String COMMENT_ENDPOINT = "/api/v1/comment";

    private final CommentService commentService;

    @Value("${quiz.resource-url-format:%s/%s}")
    private String resourceUrlFormat;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCommentById(@PathVariable UUID id) {
    commentService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@Validated @PathVariable UUID id) {
        return commentService
            .getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CreateUpdateCommentDto comment) {
        CommentDto createdComment = commentService.createComment(comment);
        URI resourceUrl = URI.create(String.format(resourceUrlFormat, COMMENT_ENDPOINT, createdComment.getId()));
        return ResponseEntity.created(resourceUrl).body(createdComment);
    }
}
