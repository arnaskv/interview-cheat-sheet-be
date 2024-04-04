package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InterviewQuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;
    private static final InterviewQuestionMapper MAPPER = InterviewQuestionMapper.INSTANCE;

    public Optional<InterviewQuestionResponseDto> findById(Long interviewId) {
        Optional<InterviewQuestion> optionalInterviewQuestion = interviewQuestionRepository.findById(interviewId);
        return optionalInterviewQuestion.map(InterviewQuestionResponseDto::of);
    }

    public List<InterviewQuestionResponseDto> getAllInterviewQuestions() {
        return interviewQuestionRepository
                .findAll()
                .stream()
                .map(InterviewQuestionResponseDto::of)
                .collect(Collectors.toList());
    }

    public InterviewQuestionResponseDto createInterviewQuestion(@Valid InterviewQuestionRequestDto requestDto) {
        InterviewQuestion interviewQuestion = MAPPER.requestDtoToInterviewQuestion(requestDto);
        InterviewQuestion createdInterviewQuestion = interviewQuestionRepository.save(interviewQuestion);
        return InterviewQuestionResponseDto.of(createdInterviewQuestion);
    }
}
