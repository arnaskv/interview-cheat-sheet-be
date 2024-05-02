package com.interview.manager.backend.controllers;

import java.util.List;
import java.util.UUID;

import com.interview.manager.backend.dto.CommentEditRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.manager.backend.dto.CommentResponseDto;
import com.interview.manager.backend.services.comment.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable UUID id) {
        return commentService
            .getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCommentById(@PathVariable UUID id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<CommentResponseDto> editComment(@Valid @RequestBody CommentEditRequestDto requestDto) {
        var updatedComment = commentService.editComment(requestDto);
        return ResponseEntity.ok(updatedComment);
    }
}
