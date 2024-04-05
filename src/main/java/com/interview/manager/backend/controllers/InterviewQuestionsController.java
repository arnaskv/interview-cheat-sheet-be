package com.interview.manager.backend.controllers;

import com.interview.manager.backend.models.InterviewQuestionResponseDto;
import com.interview.manager.backend.services.interviewQuestion.InterviewQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/interview-questions")
@Validated
public class InterviewQuestionsController {

    private final InterviewQuestionService interviewQuestionService;

    @GetMapping("/{id}")
    public ResponseEntity<InterviewQuestionResponseDto> getInterviewQuestions(@PathVariable Long id) {
        Optional<InterviewQuestionResponseDto> interviewQuestion = interviewQuestionService.findById(id);
        return interviewQuestion
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestionResponseDto>> getAllInterviewQuestions() {
        List<InterviewQuestionResponseDto> interviewQuestionResponseDto = interviewQuestionService.getAllInterviewQuestions();
        return interviewQuestionResponseDto.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(interviewQuestionResponseDto);
    }
}
