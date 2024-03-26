package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.entities.InterviewQuestion;
import com.interview.manager.backend.models.InterviewQuestionResponseDto;
import com.interview.manager.backend.repositories.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewQuestionService {
    private final InterviewRepository interviewRepository;

    public InterviewQuestionService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public List<InterviewQuestionResponseDto> findInterviewQuestionsDtoByInterviewId(Long interviewId) {
        List<InterviewQuestion> interviewQuestions = interviewRepository.findAllByInterviewId(interviewId);
        return interviewQuestions.stream()
                .map(InterviewQuestionResponseDto::of)
                .collect(Collectors.toList());
    }
}
