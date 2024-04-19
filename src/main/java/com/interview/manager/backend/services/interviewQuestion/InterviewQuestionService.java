package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionEditRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    public ResponseEntity<InterviewQuestionResponseDto> createInterviewQuestion(InterviewQuestionRequestDto requestDto) {

        InterviewQuestion interviewQuestion = MAPPER.requestDtoToInterviewQuestion(requestDto);
        InterviewQuestion createdInterviewQuestion = interviewQuestionRepository.save(interviewQuestion);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/api/v1/interview-questions")
            .buildAndExpand(createdInterviewQuestion.getId())
            .toUri();

        return ResponseEntity.created(location).body(InterviewQuestionResponseDto.of(createdInterviewQuestion));
    }

    public ResponseEntity<InterviewQuestionResponseDto> editInterviewQuestion(InterviewQuestionEditRequestDto requestDto) {
        Optional<InterviewQuestion> interviewQuestion = interviewQuestionRepository.findById(requestDto.getId());
        interviewQuestion.ifPresentOrElse(
            question -> {
                question.setTitle(requestDto.getTitle());
                interviewQuestionRepository.save(question);
            },
            () -> {
                throw new NoSuchElementException("Question with ID " + requestDto.getId() + " not found");
            }
        );

        return ResponseEntity.ok().body(InterviewQuestionResponseDto.of(interviewQuestion.get()));
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
