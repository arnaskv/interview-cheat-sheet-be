package com.interview.manager.backend.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.manager.backend.dto.CommentResponseDto;
import com.interview.manager.backend.dto.CommentRequestDto;
import com.interview.manager.backend.services.comment.CommentService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable UUID id) {
        return commentService
            .getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/interview-questions/{questionId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByQuestionId(@PathVariable Long questionId) {
        List<CommentResponseDto> comments = commentService.getAllByQuestionId(questionId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/interview-questions/{questionId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long questionId,@Valid @RequestBody CommentRequestDto comment) {
        CommentResponseDto createdComment = commentService.create(questionId, comment);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdComment.getId())
            .toUri();
        return ResponseEntity.created(location).body(createdComment);
    }

    @DeleteMapping("/comments/{id}")
        public ResponseEntity<Void> deleteCommentById(@PathVariable UUID id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
