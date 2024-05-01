package com.interview.manager.backend.controllers;

import com.interview.manager.backend.dto.CommentRequestDto;
import com.interview.manager.backend.dto.CommentResponseDto;
import com.interview.manager.backend.dto.InterviewQuestionEditRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.services.comment.CommentService;
import com.interview.manager.backend.services.interviewQuestion.InterviewQuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/interview-questions")
@Validated
public class InterviewQuestionsController {

    private final InterviewQuestionService interviewQuestionService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<InterviewQuestionResponseDto> getInterviewQuestions(@PathVariable Long id) {
        Optional<InterviewQuestionResponseDto> interviewQuestion = interviewQuestionService.findById(id);
        return interviewQuestion
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.ok(new InterviewQuestionResponseDto()));
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestionResponseDto>> getAllInterviewQuestions() {
        List<InterviewQuestionResponseDto> interviewQuestionResponseDto = interviewQuestionService.getAllInterviewQuestions();
        return ResponseEntity.ok(interviewQuestionResponseDto);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByQuestionId(@PathVariable Long id) {
        List<CommentResponseDto> comments = commentService.getAllByQuestionId(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDto comment) {
        CommentResponseDto createdComment = commentService.create(id, comment);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdComment.getId())
            .toUri();
        return ResponseEntity.created(location).body(createdComment);
    }

    @PostMapping
    public ResponseEntity<InterviewQuestionResponseDto> createInterviewQuestion(@Valid @RequestBody InterviewQuestionRequestDto requestDto) {
        InterviewQuestionResponseDto questionResponse = interviewQuestionService.createInterviewQuestion(requestDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(questionResponse.getId())
            .toUri();

        return ResponseEntity.created(location).body(questionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewQuestionByid(@Valid @PathVariable Long id) {
        interviewQuestionService.deleteInterviewQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<InterviewQuestionResponseDto> editInterviewQuestion(@Valid @RequestBody InterviewQuestionEditRequestDto requestDto) {
        var updatedQuestion = interviewQuestionService.editInterviewQuestion(requestDto);
        return ResponseEntity.ok().body(updatedQuestion);
    }
}
