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

import com.interview.manager.backend.dto.CommentResponseDto;
import com.interview.manager.backend.dto.CommentRequestDto;
import com.interview.manager.backend.services.comment.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = CommentController.BASE_ENDPOINT)
public class CommentController {
    public static final String BASE_ENDPOINT = "/api/v1";

    private final CommentService commentService;

    @Value("${quiz.resource-url-format:%s/%s}")
    private String resourceUrlFormat;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@Validated @PathVariable UUID id) {
        CommentResponseDto comment = commentService.getById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("interview-questions/{questionId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByQuestionId(@PathVariable Long questionId) {
        List<CommentResponseDto> comments = commentService.getAllByQuestionId(questionId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("interview-questions/{questionId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long questionId, @RequestBody CommentRequestDto comment) {
        CommentResponseDto createdComment = commentService.create(questionId, comment);
        URI resourceUrl = URI.create(String.format(resourceUrlFormat, BASE_ENDPOINT + "/comments", createdComment.getId()));
        return ResponseEntity.created(resourceUrl).body(createdComment);
    }

    @DeleteMapping("/comments/{id}")
        public ResponseEntity<Void> deleteCommentById(@PathVariable UUID id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
