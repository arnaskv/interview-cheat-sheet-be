package com.interview.manager.backend.controllers;

import com.interview.manager.backend.entities.InterviewQuestion;
import com.interview.manager.backend.models.InterviewQuestionResponseDto;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interview-questions")
public class InterviewQuestionsController {

    private final InterviewQuestionRepository interviewQuestionRepository;

    public InterviewQuestionsController(InterviewQuestionRepository interviewQuestionRepository) {
        this.interviewQuestionRepository = interviewQuestionRepository;
    }

    @GetMapping("/{id}")
    public InterviewQuestionResponseDto getInterviewQuestions(@PathVariable Long id) {
        InterviewQuestion interviewQuestion = interviewQuestionRepository.findById(id).get();
        return InterviewQuestionResponseDto.builder()
                .id(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .build();
    }
}
