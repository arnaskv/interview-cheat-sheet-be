package com.interview.manager.backend.controllers;

import com.interview.manager.backend.models.InterviewQuestionResponseDto;
import com.interview.manager.backend.services.interviewQuestion.InterviewQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/interview-questions")
public class InterviewQuestionsController {

    private final InterviewQuestionService interviewQuestionService;

    @GetMapping("/{id}")
    public InterviewQuestionResponseDto getInterviewQuestions(@PathVariable UUID id) {
        return interviewQuestionService.findById(id);
    }
}
