package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionEditRequestDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        List<InterviewQuestion> parentQuestions = interviewQuestionRepository.findAllByParentQuestionIsNull();

        return parentQuestions.stream()
            .map(parentQuestion -> {
                InterviewQuestionResponseDto parentResponseDto = MAPPER.questionToResponseDto(parentQuestion);
                List<InterviewQuestionResponseDto> subQuestionsDtoList = parentQuestion.getSubQuestions().stream()
                    .map(MAPPER::questionToResponseDto)
                    .toList();
                parentResponseDto.setSubQuestions(subQuestionsDtoList);
                return parentResponseDto;
            })
            .toList();
    }

    public InterviewQuestionResponseDto createInterviewQuestion(InterviewQuestionRequestDto requestDto) {

        Category category = categoryRepository.findById(requestDto.getCategoryId())
            .orElseThrow(() -> new DataValidationException(DataValidation.Status.NOT_FOUND, "Category not found"));

        InterviewQuestion parentQuestion = interviewQuestionRepository.findById(requestDto.getParentId())
            .orElse(null);

        InterviewQuestion interviewQuestion = MAPPER.requestDtoToInterviewQuestion(requestDto);
        interviewQuestion.setCategory(category);
        interviewQuestion.setParentQuestion(parentQuestion);

        return MAPPER.questionToResponseDto(interviewQuestionRepository.save(interviewQuestion));
    }

    public InterviewQuestionResponseDto editInterviewQuestion(InterviewQuestionEditRequestDto requestDto) {
        Optional<InterviewQuestion> interviewQuestion = interviewQuestionRepository.findById(requestDto.getId());
        return interviewQuestion.map(question -> {
            question.setTitle(requestDto.getTitle());
            interviewQuestionRepository.save(question);
            return MAPPER.questionToResponseDto(question);
        }).orElseThrow(() -> new NoSuchElementException("Question with ID " + requestDto.getId() + " not found"));
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
