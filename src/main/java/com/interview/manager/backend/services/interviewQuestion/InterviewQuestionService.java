package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.entities.InterviewQuestion;
import com.interview.manager.backend.models.InterviewQuestionResponseDto;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;

    public InterviewQuestionService(InterviewQuestionRepository interviewQuestionRepository) {
        this.interviewQuestionRepository = interviewQuestionRepository;
    }

    public InterviewQuestionResponseDto findById(Long interviewId) {
        InterviewQuestion interviewQuestion = interviewQuestionRepository.findById(interviewId).get();
        return InterviewQuestionResponseDto.builder()
                .id(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .build();
    }
}
