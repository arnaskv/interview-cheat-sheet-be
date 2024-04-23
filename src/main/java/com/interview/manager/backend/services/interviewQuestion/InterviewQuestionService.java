package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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

    @Transactional
    public InterviewQuestionResponseDto createInterviewQuestion(InterviewQuestionRequestDto requestDto) {
        InterviewQuestion interviewQuestion = MAPPER.requestDtoToInterviewQuestion(requestDto);
        InterviewQuestion createdInterviewQuestion = interviewQuestionRepository.save(interviewQuestion);
        return InterviewQuestionResponseDto.of(createdInterviewQuestion);
    }

    @Transactional
    public void deleteInterviewQuestionById(Long id) {
        interviewQuestionRepository.findById(id)
            .ifPresentOrElse(
                interviewQuestion -> interviewQuestionRepository.deleteById(id),
                () -> {
                    throw new NoSuchElementException("Interview question with an ID " + id + " not found");
                }
            );
    }
}
