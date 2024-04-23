package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.exceptions.DataValidationException;
import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.CategoryRepository;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import com.interview.manager.backend.types.DataValidation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CategoryRepository categoryRepository;
    private static final InterviewQuestionMapper MAPPER = InterviewQuestionMapper.INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(InterviewQuestion.class);

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

        if (requestDto.getCategoryId() == null) {
            logger.error("Category id is required");
            throw new DataValidationException(DataValidation.Status.MISSING_DATA);
        }

        Optional<Category> category = categoryRepository.findById(requestDto.getCategoryId());
        if (category.isEmpty()) {
            logger.error("Category not found");
            throw new DataValidationException(DataValidation.Status.NOT_FOUND);
        }

        InterviewQuestion interviewQuestion = MAPPER.requestDtoToInterviewQuestion(requestDto);
        //Maybe there is a better way to map this
        category.ifPresent(interviewQuestion::setCategory);
        InterviewQuestion createdInterviewQuestion = interviewQuestionRepository.save(interviewQuestion);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/api/v1/interview-questions")
            .buildAndExpand(createdInterviewQuestion.getId())
            .toUri();

        return ResponseEntity.created(location).body(InterviewQuestionResponseDto.of(createdInterviewQuestion));
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
