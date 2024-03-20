package com.interview.manager.backend.controllers;

import com.interview.manager.backend.models.InterviewQuestionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interview-questions")
public class InterviewQuestionsController {

    @GetMapping("{id}")
    public List<InterviewQuestionDto> getAll(@PathVariable Long id) {
        return List.of(new InterviewQuestionDto(1L, "Test", 1L));
    }
}
