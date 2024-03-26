package com.interview.manager.backend.controllers;

import com.interview.manager.backend.models.InterviewQuestionRequestDto;
import com.interview.manager.backend.models.InterviewQuestionResponseDto;
import com.interview.manager.backend.services.interviewQuestion.InterviewQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/v1/interviews")
public class InterviewController {
    private final InterviewQuestionService interviewQuestionService;

    public InterviewController(InterviewQuestionService interviewQuestionService) {

        this.interviewQuestionService = interviewQuestionService;
    }

    @GetMapping("/{id}")
    public List<InterviewQuestionResponseDto> getInterviewQuestions(@PathVariable Long id) {
        List<InterviewQuestionResponseDto> interviewQuestionResponseDtos = interviewQuestionService.findInterviewQuestionsDtoByInterviewId(id);
        return interviewQuestionResponseDtos;
    }

    @GetMapping("/{interviewId}/questions/{questionId}")
    public InterviewQuestionRequestDto getInterviewQuestion(@PathVariable Long interviewId, @PathVariable Long questionId) {
        List<InterviewQuestionResponseDto> interviewQuestionResponseDtos = interviewQuestionService.findInterviewQuestionsDtoByInterviewId(interviewId);
        Predicate<InterviewQuestionResponseDto> interviewQuestion = iq -> iq.getId().equals(questionId);
        return interviewQuestionResponseDtos.stream().filter(interviewQuestion).findFirst().orElse(null);
    }
}
